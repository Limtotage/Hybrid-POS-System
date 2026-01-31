package com.example.hybridpos.dto;

import java.util.List;

import com.example.hybridpos.enums.PaymentType;

import lombok.Data;

@Data
public class SaleCreateDTO {
    private List<SaleItemDTO> items;

    private PaymentType paymentType;

    private double cashPaid;
    private double cardPaid;
}
