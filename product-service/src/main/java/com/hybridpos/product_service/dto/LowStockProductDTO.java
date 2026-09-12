package com.hybridpos.product_service.dto;

import lombok.Data;

@Data
public class LowStockProductDTO {

    private Long id;

    private String name;

    private String barcode;

    private int stockQuantity;
}