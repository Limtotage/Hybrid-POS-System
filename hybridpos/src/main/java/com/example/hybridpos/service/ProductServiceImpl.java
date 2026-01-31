package com.example.hybridpos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;
import com.example.hybridpos.entity.Product;
import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.interfaces.ProductInterface;
import com.example.hybridpos.repository.ProductRepository;
import com.example.hybridpos.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInterface{

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    @Override
    public ProductResponseDTO createProduct(Long shopId, ProductCreateDTO dto) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Product product = new Product();
        product.setBarcode(dto.getBarcode());
        product.setName(dto.getName());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setSalePrice(dto.getSalePrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setShop(shop);
        product.setCreatedAt(LocalDateTime.now());

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
    public List<ProductResponseDTO> getAllProducts(Long shopId) {
        List<Product> products = productRepository.getAllProducts(shopId);
        return products.stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow();
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public ProductResponseDTO updatePrice(Long productId, ProductPriceUpdateDTO dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        double price = product.getSalePrice();

        if (dto.isPercent()) {
            price += price * dto.getAmount() / 100;
        } else {
            price += dto.getAmount();
        }

        product.setSalePrice(price);
        productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    public void increaseStock(String barcode, int amount) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow();
        product.setStockQuantity(product.getStockQuantity() + amount);
        productRepository.save(product);
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
        return dto;
    }
}
