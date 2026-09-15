package com.hybridpos.report_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementDTO {

    private Long id;

    private Long productId;

    private String type;

    private Integer quantity;

    private LocalDateTime createdAt;
}