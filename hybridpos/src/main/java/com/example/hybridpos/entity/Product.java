package com.example.hybridpos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    name = "products",
    uniqueConstraints = @UniqueConstraint(
        columnNames = "barcode"
    )
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false)
    private String name;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private int stockQuantity;

    private boolean active = true;

    private LocalDateTime createdAt;
}

