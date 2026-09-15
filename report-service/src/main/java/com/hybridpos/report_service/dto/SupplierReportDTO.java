package com.hybridpos.report_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierReportDTO {

    private Long productId;

    private String productName;

    private String barcode;

    private LocalDateTime lastSupplyDate;

    private Integer suppliedQuantity;

    private Integer soldQuantity;

    private Integer remainingQuantity;
}