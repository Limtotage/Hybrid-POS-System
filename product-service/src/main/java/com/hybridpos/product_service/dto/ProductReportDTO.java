package com.hybridpos.product_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductReportDTO {

    private List<LowStockProductDTO> lowStockProducts;
}