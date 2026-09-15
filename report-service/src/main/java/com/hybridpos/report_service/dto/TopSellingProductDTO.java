package com.hybridpos.report_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSellingProductDTO {

    private Long productId;
    private String productName;
    private String barcode;
    private Long soldQuantity;
}