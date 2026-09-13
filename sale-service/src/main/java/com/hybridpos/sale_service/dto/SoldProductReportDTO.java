package com.hybridpos.sale_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SoldProductReportDTO {

    private String productName;

    private String barcode;

    private int quantity;

    private BigDecimal totalAmount;
}