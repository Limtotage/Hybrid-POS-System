package com.hybridpos.cash_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashRegisterReportDTO {

    private Long cashRegisterId;

    private String cashRegisterName;

    private boolean open;

    private BigDecimal totalCashSales;

    private BigDecimal totalCardSales;

    private BigDecimal totalSales;
}