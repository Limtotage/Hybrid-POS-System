package com.example.hybridpos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String barcode;
    private String name;

    private BigDecimal purchasePrice;
    private BigDecimal salePrice;

    private int stockQuantity;
    private boolean active;
}
