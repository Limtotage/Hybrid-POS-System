package com.hybridpos.sale_service.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class SaleReportDTO {

    private long totalSales;

    private BigDecimal totalRevenue;

    private BigDecimal totalCash;

    private BigDecimal totalCard;
    private List<SoldProductReportDTO> soldProducts;
}