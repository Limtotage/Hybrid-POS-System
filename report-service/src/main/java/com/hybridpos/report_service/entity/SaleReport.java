package com.hybridpos.report_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long saleId;

    private Long cashRegisterId;

    private BigDecimal totalAmount;

    private BigDecimal cashPaid;

    private BigDecimal cardPaid;

    private String paymentType;

    private LocalDateTime createdAt;
}