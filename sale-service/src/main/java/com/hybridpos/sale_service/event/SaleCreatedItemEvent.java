package com.hybridpos.sale_service.event;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreatedItemEvent {

    private Long productId;

    private String barcode;

    private String productName;

    private BigDecimal priceAtSale;

    private Integer quantity;
}