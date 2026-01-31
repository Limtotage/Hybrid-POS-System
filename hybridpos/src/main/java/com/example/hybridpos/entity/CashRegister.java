package com.example.hybridpos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CashRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double openingCash;
    private double closingCash;

    private double totalCashSales;
    private double totalCardSales;

    private boolean open;

    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}

