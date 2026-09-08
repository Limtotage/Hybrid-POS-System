package com.example.hybridpos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;
import com.example.hybridpos.entity.Product;
import com.example.hybridpos.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO dto) {

        Product product = new Product();
        product.setBarcode(dto.getBarcode());
        product.setName(dto.getName());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setSalePrice(dto.getSalePrice());
        product.setStockQuantity(dto.getStockQuantity());
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
