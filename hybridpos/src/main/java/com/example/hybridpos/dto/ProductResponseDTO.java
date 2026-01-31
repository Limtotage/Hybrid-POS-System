package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String barcode;
    private String name;

    private double purchasePrice;
    private double salePrice;

    private int stockQuantity;
    private boolean active;
}
