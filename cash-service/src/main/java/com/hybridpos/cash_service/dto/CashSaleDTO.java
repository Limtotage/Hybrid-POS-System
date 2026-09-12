package com.hybridpos.cash_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashSaleDTO {

    private BigDecimal cashPaid;

    private BigDecimal cardPaid;
}