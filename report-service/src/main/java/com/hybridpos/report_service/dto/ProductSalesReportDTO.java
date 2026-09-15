package com.hybridpos.report_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesReportDTO {

    private Long productId;
    private String productName;
    private String barcode;
    private Long soldQuantity;
    private BigDecimal totalRevenue;
}