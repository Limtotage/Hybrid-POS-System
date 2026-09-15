package com.hybridpos.report_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hybridpos.report_service.entity.SaleReport;
import com.hybridpos.report_service.service.SaleReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class SaleReportController {

    private final SaleReportService saleReportService;

    @GetMapping("/sales")
    public List<SaleReport> getAllSales() {
        return saleReportService.getAllSales();
    }
}