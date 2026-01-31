package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class ProductPriceUpdateDTO {
    private double amount;
    private boolean percent;
}
