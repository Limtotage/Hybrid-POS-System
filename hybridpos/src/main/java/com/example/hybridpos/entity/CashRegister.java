package com.example.hybridpos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class CashRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal openingCash;
    private BigDecimal closingCash;

    private BigDecimal totalCashSales;
    private BigDecimal totalCardSales;

    private boolean open;
    
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    
    @ManyToOne
    private MyUser cashier;
    
}

