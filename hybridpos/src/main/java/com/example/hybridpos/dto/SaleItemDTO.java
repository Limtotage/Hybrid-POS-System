package com.example.hybridpos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleItemDTO {
    private String barcode;
    private String name;
    private BigDecimal salePrice;
    private int quantity;
}
