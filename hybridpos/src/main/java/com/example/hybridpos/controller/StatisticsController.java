package com.example.hybridpos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.repository.SaleRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class StatisticsController {

    private final SaleRepository saleRepository;

    @GetMapping("/total-sales")
    public Double totalSales() {
        return saleRepository.sumTotalSales();
    }

    @GetMapping("/best-seller")
    public Object bestSeller() {
        return saleRepository.findTopSellingProducts();
    }
}

