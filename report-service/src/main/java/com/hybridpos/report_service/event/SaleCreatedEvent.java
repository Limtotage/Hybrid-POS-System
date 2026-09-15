package com.hybridpos.report_service.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreatedEvent {

    private Long saleId;
    private Long cashRegisterId;
    private BigDecimal totalAmount;
    private BigDecimal cashPaid;
    private BigDecimal cardPaid;
    private String paymentType;
    private LocalDateTime createdAt;
    private List<SaleCreatedItemEvent> items;
}