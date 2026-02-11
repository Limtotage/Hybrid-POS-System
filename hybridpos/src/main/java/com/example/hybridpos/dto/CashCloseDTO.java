package com.example.hybridpos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashCloseDTO {
    private BigDecimal closingCash;
    private String cashName;
    private String username;
}
