package com.hybridpos.sale_service.dto;


import java.math.BigDecimal;
import java.util.List;

import com.hybridpos.sale_service.enums.PaymentType;

import lombok.Data;

@Data
public class SaleCreateDTO {
    private String clientSaleId;
    private List<SaleItemDTO> items;

    private PaymentType paymentType;

    private BigDecimal cashPaid;
    private BigDecimal cardPaid;
}
