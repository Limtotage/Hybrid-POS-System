package com.hybridpos.product_service.dto;


import lombok.Data;

@Data
public class ProductPriceUpdateDTO {
    private double amount;
    private boolean percent;
}
