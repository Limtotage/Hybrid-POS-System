package com.hybridpos.sale_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product başka bir mikroserviste.
    // Bu yüzden Product entity'sine @ManyToOne yapmıyoruz.
    private Long productId;

    private String barcode;

    private String productName;

    private int quantity;

    // Satış anındaki fiyatı saklıyoruz.
    private BigDecimal priceAtSale;

    @ManyToOne
    private Sale sale;
}