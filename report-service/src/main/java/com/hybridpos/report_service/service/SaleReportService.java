package com.hybridpos.report_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hybridpos.report_service.entity.SaleReport;
import com.hybridpos.report_service.repository.SaleReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleReportService {

    private final SaleReportRepository saleReportRepository;

    public List<SaleReport> getAllSales() {
        return saleReportRepository.findAll();
    }
}