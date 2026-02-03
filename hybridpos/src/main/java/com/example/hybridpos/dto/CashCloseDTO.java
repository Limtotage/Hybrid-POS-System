package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class CashCloseDTO {
    private double closingCash;
    private String cashName;
    private String username;
}
