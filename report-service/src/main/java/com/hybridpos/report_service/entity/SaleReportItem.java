package com.hybridpos.report_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_report_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleReportItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long saleId;

    private Long productId;

    private String barcode;

    private String productName;

    private BigDecimal priceAtSale;

    private Integer quantity;
    private LocalDateTime createdAt;
}