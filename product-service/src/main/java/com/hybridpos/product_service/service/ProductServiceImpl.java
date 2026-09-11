package com.hybridpos.product_service.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hybridpos.product_service.dto.ProductCreateDTO;
import com.hybridpos.product_service.dto.ProductPriceUpdateDTO;
import com.hybridpos.product_service.dto.ProductResponseDTO;
import com.hybridpos.product_service.entity.Product;
import com.hybridpos.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO dto,
                                            MultipartFile image){

        Product product = new Product();
        product.setBarcode(dto.getBarcode());
        product.setName(dto.getName());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setSalePrice(dto.getSalePrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCreatedAt(LocalDateTime.now());
        if (image != null && !image.isEmpty()) {
            String imageUrl="";
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
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        productRepository.delete(product);
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

        return mapToResponse(product);
    }

    @Override
public void increaseStock(long productId, int amount) {

    Product product = productRepository.findById(productId)
            .orElseThrow();

    System.out.println("===== STOCK UPDATE =====");
    System.out.println("PRODUCT ID: " + productId);
    System.out.println("OLD STOCK: " + product.getStockQuantity());
    System.out.println("AMOUNT: " + amount);

    product.setStockQuantity(
            product.getStockQuantity() + amount
    );

    System.out.println(
            "NEW STOCK: " + product.getStockQuantity()
    );

    Product savedProduct = productRepository.save(product);

    System.out.println(
            "SAVED STOCK: " + savedProduct.getStockQuantity()
    );
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
}
