package com.hybridpos.report_service.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hybridpos.report_service.dto.ProductSalesReportDTO;
import com.hybridpos.report_service.dto.SaleSummaryDTO;
import com.hybridpos.report_service.dto.SupplierReportDTO;
import com.hybridpos.report_service.dto.TopSellingProductDTO;
import com.hybridpos.report_service.entity.SaleReport;
import com.hybridpos.report_service.service.SaleReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class SaleReportController {

        private final SaleReportService saleReportService;

        @GetMapping("/sales")
        @PreAuthorize("hasRole('ADMIN')")
        public List<SaleReport> getAllSales() {
                return saleReportService.getAllSales();
        }

        @GetMapping("/summary")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<SaleSummaryDTO> getSaleSummary() {
                return ResponseEntity.ok(saleReportService.getSaleSummary());
        }

        @GetMapping("/summary/today")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<SaleSummaryDTO> getTodaySummary() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today.atStartOfDay();
                LocalDateTime end = today.plusDays(1).atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getSaleSummaryBetween(start, end));
        }

        @GetMapping("/summary/week")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<SaleSummaryDTO> getWeeklySummary() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today.with(java.time.DayOfWeek.MONDAY).atStartOfDay();

                LocalDateTime end = start.plusWeeks(1);

                return ResponseEntity.ok(
                                saleReportService.getSaleSummaryBetween(start, end));
        }

        @GetMapping("/summary/year")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<SaleSummaryDTO> getYearSummary() {

                LocalDate today = LocalDate.now();
                LocalDateTime start = today.withDayOfYear(1).atStartOfDay();
                LocalDateTime end = today.plusYears(1)
                                .withDayOfYear(1)
                                .atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getSaleSummaryBetween(start, end));
        }

        @GetMapping("/summary/month")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<SaleSummaryDTO> getMonthSummary() {

                LocalDate today = LocalDate.now();
                LocalDateTime start = today.withDayOfMonth(1).atStartOfDay();
                LocalDateTime end = today.plusMonths(1)
                                .withDayOfMonth(1)
                                .atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getSaleSummaryBetween(start, end));
        }

        @GetMapping("/products/month")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<ProductSalesReportDTO>> getMonthlyProductSales() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today
                                .withDayOfMonth(1)
                                .atStartOfDay();

                LocalDateTime end = today
                                .plusMonths(1)
                                .withDayOfMonth(1)
                                .atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getProductSalesReport(start, end));
        }

        @GetMapping("/products/year")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<ProductSalesReportDTO>> getYearlyProductSales() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today
                                .withDayOfYear(1)
                                .atStartOfDay();

                LocalDateTime end = today
                                .plusYears(1)
                                .withDayOfYear(1)
                                .atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getProductSalesReport(start, end));
        }

        @GetMapping("/products/today")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<ProductSalesReportDTO>> getTodaysProductSales() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today.atStartOfDay();
                LocalDateTime end = today.plusDays(1).atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getProductSalesReport(start, end));
        }

        @GetMapping("/products/week")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<ProductSalesReportDTO>> getWeeklyProductSales() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today.with(java.time.DayOfWeek.MONDAY).atStartOfDay();

                LocalDateTime end = start.plusWeeks(1);

                return ResponseEntity.ok(
                                saleReportService.getProductSalesReport(start, end));
        }

        @GetMapping("/products/top/year")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProductsYear() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today
                                .withDayOfYear(1)
                                .atStartOfDay();

                LocalDateTime end = today
                                .plusYears(1)
                                .withDayOfYear(1)
                                .atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getTopSellingProducts(start, end));
        }

        @GetMapping("/products/top/month")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProductsMonth() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today
                                .withDayOfMonth(1)
                                .atStartOfDay();

                LocalDateTime end = today
                                .plusMonths(1)
                                .withDayOfMonth(1)
                                .atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getTopSellingProducts(start, end));
        }

        @GetMapping("/products/top/today")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProductsToday() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today.atStartOfDay();
                LocalDateTime end = today.plusDays(1).atStartOfDay();

                return ResponseEntity.ok(
                                saleReportService.getTopSellingProducts(start, end));
        }

        @GetMapping("/products/top/week")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProductsWeek() {

                LocalDate today = LocalDate.now();

                LocalDateTime start = today.with(java.time.DayOfWeek.MONDAY).atStartOfDay();

                LocalDateTime end = start.plusWeeks(1);

                return ResponseEntity.ok(
                                saleReportService.getTopSellingProducts(start, end));
        }

        @GetMapping("/products/top/all")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProductsAllTime() {

                return ResponseEntity.ok(
                                saleReportService.getTopSellingProductsAllTime());
        }

        @GetMapping("/supplier/{productId}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<SupplierReportDTO> getSoldQuantitySinceLastPurchase(
                        @PathVariable Long productId,
                        @RequestHeader("Authorization") String authorizationHeader) {

                String token = authorizationHeader.replace("Bearer ", "");

                SupplierReportDTO supplierReport = saleReportService.getSupplierReport(
                                productId,
                                token);

                return ResponseEntity.ok(supplierReport);
        }

        @GetMapping("/supplier")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<SupplierReportDTO>> getAllSupplierReports(
                        @RequestHeader("Authorization") String authorizationHeader) {

                String token = authorizationHeader.replace("Bearer ", "");

                List<SupplierReportDTO> reports = saleReportService.getAllSupplierReports(token);

                return ResponseEntity.ok(reports);
        }
}