package com.example.hybridpos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashOpenDTO {
    private String cashName;
    private BigDecimal openingCash;
    private String username;
}
