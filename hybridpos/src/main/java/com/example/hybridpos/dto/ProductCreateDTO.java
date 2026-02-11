package com.example.hybridpos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductCreateDTO {
    
    private String barcode;
    private String name;

    private BigDecimal purchasePrice;
    private BigDecimal salePrice;

    private int stockQuantity;
}
