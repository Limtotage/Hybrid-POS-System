package com.example.hybridpos.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.hybridpos.enums.PaymentType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private double cashPaid;
    private double cardPaid;

    private LocalDateTime saleDate;

    @ManyToOne
    private CashRegister cashRegister;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items;
}

