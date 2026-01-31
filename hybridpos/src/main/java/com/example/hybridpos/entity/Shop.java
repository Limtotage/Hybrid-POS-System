package com.example.hybridpos.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;



@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<CashRegister> cashRegisters;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> products;
}