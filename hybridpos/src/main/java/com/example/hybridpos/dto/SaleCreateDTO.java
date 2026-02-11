package com.example.hybridpos.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.hybridpos.enums.PaymentType;

import lombok.Data;

@Data
public class SaleCreateDTO {
    private List<SaleItemDTO> items;

    private PaymentType paymentType;

    private BigDecimal cashPaid;
    private BigDecimal cardPaid;
}
