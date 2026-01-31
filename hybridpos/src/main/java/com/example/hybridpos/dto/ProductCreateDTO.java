package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class ProductCreateDTO {
    
    private String barcode;
    private String name;

    private double purchasePrice;
    private double salePrice;

    private int stockQuantity;
}
