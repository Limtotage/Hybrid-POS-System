package com.hybridpos.product_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductUpdateDTO {

    private String barcode;

    private String name;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;
}