package com.hybridpos.report_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleSummaryDTO {

    private Long totalSales;
    private BigDecimal totalRevenue;
    private BigDecimal totalCash;
    private BigDecimal totalCard;
}
