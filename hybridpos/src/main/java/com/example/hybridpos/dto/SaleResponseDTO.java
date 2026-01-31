package com.example.hybridpos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SaleResponseDTO {
    private Long saleId;
    private double totalAmount;
    private LocalDateTime date;
}
