package com.hybridpos.report_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hybridpos.report_service.client.ProductClient;
import com.hybridpos.report_service.dto.StockMovementDTO;
import com.hybridpos.report_service.dto.SupplierReportDTO;
import com.hybridpos.report_service.entity.SaleReport;
import com.hybridpos.report_service.service.SaleReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class SaleReportController {

    private final SaleReportService saleReportService;
    private final ProductClient productClient;

    @GetMapping("/sales")
    public List<SaleReport> getAllSales() {
        return saleReportService.getAllSales();
    }

    @GetMapping("/test/product/{productId}")
    public List<StockMovementDTO> testProductClient(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        return productClient.getPurchaseMovements(productId, token);
    }

    @GetMapping("/supplier/{productId}")
    public ResponseEntity<SupplierReportDTO> getSoldQuantitySinceLastPurchase(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        SupplierReportDTO supplierReport = saleReportService.getSupplierReport(
                productId,
                token);

        return ResponseEntity.ok(supplierReport);
    }
}