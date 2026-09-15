package com.hybridpos.product_service.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hybridpos.product_service.dto.LowStockProductDTO;
import com.hybridpos.product_service.dto.ProductCreateDTO;
import com.hybridpos.product_service.dto.ProductPriceUpdateDTO;
import com.hybridpos.product_service.dto.ProductReportDTO;
import com.hybridpos.product_service.dto.ProductResponseDTO;
import com.hybridpos.product_service.entity.Product;
import com.hybridpos.product_service.entity.StockMovement;
import com.hybridpos.product_service.enums.StockMovementType;
import com.hybridpos.product_service.repository.ProductRepository;
import com.hybridpos.product_service.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final FileStorageService fileStorageService;
    private final CacheManager cacheManager;
    private static final int LOW_STOCK_LIMIT = 5;

    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO dto,
            MultipartFile image) {

        Product product = new Product();
        product.setBarcode(dto.getBarcode());
        product.setName(dto.getName());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setSalePrice(dto.getSalePrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCreatedAt(LocalDateTime.now());
        if (image != null && !image.isEmpty()) {
            String imageUrl = "";
            try {
                imageUrl = fileStorageService.saveProductImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImageURL(imageUrl);
        }

        productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    @Cacheable(value = "products", key = "#barcode")
    public ProductResponseDTO getByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);

        cacheManager.getCache("products")
                .evict(product.getBarcode());
    }

    @Override
    public ProductResponseDTO updatePrice(long productId, ProductPriceUpdateDTO dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        BigDecimal price = product.getSalePrice();

        if (dto.isPercent()) {
            price = price.add(price.multiply(BigDecimal.valueOf(dto.getAmount()).divide(BigDecimal.valueOf(100))));
        } else {
            price = price.add(BigDecimal.valueOf(dto.getAmount()));
        }

        product.setSalePrice(price);
        productRepository.save(product);
        cacheManager.getCache("products")
                .evict(product.getBarcode());

        return mapToResponse(product);
    }

    @Override
    public void increaseStock(long productId, int amount) {

        Product product = productRepository.findById(productId)
                .orElseThrow();

        product.setStockQuantity(
                product.getStockQuantity() + amount);

        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProductId(productId);
        movement.setType(StockMovementType.PURCHASE);
        movement.setQuantity(amount);
        movement.setCreatedAt(LocalDateTime.now());

        stockMovementRepository.save(movement);

        cacheManager.getCache("products")
                .evict(product.getBarcode());
    }
    @Override
    public void decreaseStock(long productId, int amount) {

        Product product = productRepository.findById(productId)
                .orElseThrow();
        if (product.getStockQuantity() < amount) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStockQuantity(
                product.getStockQuantity() - amount);

        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProductId(productId);
        movement.setType(StockMovementType.SALE);
        movement.setQuantity(amount);
        movement.setCreatedAt(LocalDateTime.now());

        stockMovementRepository.save(movement);

        cacheManager.getCache("products")
                .evict(product.getBarcode());
    }

    private ProductResponseDTO mapToResponse(Product p) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(p.getId());
        dto.setBarcode(p.getBarcode());
        dto.setName(p.getName());
        dto.setPurchasePrice(p.getPurchasePrice());
        dto.setSalePrice(p.getSalePrice());
        dto.setStockQuantity(p.getStockQuantity());
        dto.setActive(p.isActive());
        dto.setImageUrl(p.getImageURL());
        return dto;
    }

    @Override

    public ProductReportDTO getProductReport() {

        List<Product> products = productRepository.findAll();

        List<LowStockProductDTO> lowStockProducts = products.stream()
                .filter(product -> product.getStockQuantity() <= LOW_STOCK_LIMIT)// sonradan admin tarafından
                                                                                 // aarlanabilen bir versiyona
                                                                                 // geçilebilir.
                .map(product -> {

                    LowStockProductDTO dto = new LowStockProductDTO();

                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setBarcode(product.getBarcode());
                    dto.setStockQuantity(
                            product.getStockQuantity());

                    return dto;
                })
                .toList();

        ProductReportDTO report = new ProductReportDTO();

        report.setLowStockProducts(lowStockProducts);

        return report;
    }
}
