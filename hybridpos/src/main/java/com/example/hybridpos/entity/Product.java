package com.example.hybridpos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "barcode"))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;        // barkod numarası
    private String name;

    private double purchasePrice;  // alış fiyatı
    private double salePrice;      // satış fiyatı

    private int stockQuantity;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private LocalDateTime createdAt;
}

