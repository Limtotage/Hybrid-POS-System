package com.example.hybridpos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SaleResponseDTO {
    private Long saleId;
    private BigDecimal totalAmount;
    private LocalDateTime date;
}
