package com.hybridpos.sale_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.hybridpos.sale_service.enums.PaymentType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private BigDecimal cashPaid;

    private BigDecimal cardPaid;

    private LocalDateTime saleDate;

    // Daha sonra Cash Service ile haberleşirken kullanılacak.
    // Şimdilik sadece ID tutuyoruz.
    private Long cashId;

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL
    )
    private List<SaleItem> items;
}