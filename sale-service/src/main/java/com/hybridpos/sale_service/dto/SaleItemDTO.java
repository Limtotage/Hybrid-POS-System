package com.hybridpos.sale_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleItemDTO {
    private String barcode;
    private String name;
    private BigDecimal salePrice;
    private int quantity;
}
