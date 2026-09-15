package com.hybridpos.report_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hybridpos.report_service.client.ProductClient;
import com.hybridpos.report_service.dto.ProductResponseDTO;
import com.hybridpos.report_service.dto.ProductSalesReportDTO;
import com.hybridpos.report_service.dto.SaleSummaryDTO;
import com.hybridpos.report_service.dto.StockMovementDTO;
import com.hybridpos.report_service.dto.SupplierReportDTO;
import com.hybridpos.report_service.dto.TopSellingProductDTO;
import com.hybridpos.report_service.entity.SaleReport;
import com.hybridpos.report_service.entity.SaleReportItem;
import com.hybridpos.report_service.repository.SaleReportItemRepository;
import com.hybridpos.report_service.repository.SaleReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleReportService {

        private final SaleReportRepository saleReportRepository;
        private final SaleReportItemRepository saleReportItemRepository;
        private final ProductClient productClient;

        public List<SaleReport> getAllSales() {
                return saleReportRepository.findAll();
        }

        public SaleSummaryDTO getSaleSummary() {

                Long totalSales = saleReportRepository.count();

                BigDecimal totalRevenue = saleReportRepository.getTotalRevenue();
                BigDecimal totalCash = saleReportRepository.getTotalCash();
                BigDecimal totalCard = saleReportRepository.getTotalCard();

                return new SaleSummaryDTO(
                                totalSales,
                                totalRevenue,
                                totalCash,
                                totalCard);
        }

        public SaleSummaryDTO getSaleSummaryBetween(
                        LocalDateTime start,
                        LocalDateTime end) {

                Long totalSales = saleReportRepository.countSalesBetween(start, end);

                BigDecimal totalRevenue = saleReportRepository.getRevenueBetween(start, end);

                BigDecimal totalCash = saleReportRepository.getCashBetween(start, end);

                BigDecimal totalCard = saleReportRepository.getCardBetween(start, end);

                return new SaleSummaryDTO(
                                totalSales,
                                totalRevenue,
                                totalCash,
                                totalCard);
        }

        public List<ProductSalesReportDTO> getProductSalesReport(
                        LocalDateTime start,
                        LocalDateTime end) {

                List<Object[]> results = saleReportItemRepository.getProductSalesBetween(start, end);

                return results.stream()
                                .map(row -> new ProductSalesReportDTO(
                                                (Long) row[0],
                                                (String) row[1],
                                                (String) row[2],
                                                (Long) row[3],
                                                (BigDecimal) row[4]))
                                .toList();
        }

        public List<TopSellingProductDTO> getTopSellingProducts(
                        LocalDateTime start,
                        LocalDateTime end) {

                List<Object[]> results = saleReportItemRepository.getTopSellingProducts(start, end);

                return results.stream()
                                .map(row -> new TopSellingProductDTO(
                                                (Long) row[0],
                                                (String) row[1],
                                                (String) row[2],
                                                (Long) row[3]))
                                .toList();
        }

        public List<TopSellingProductDTO> getTopSellingProductsAllTime() {

                List<Object[]> results = saleReportItemRepository.getTopSellingProductsAllTime();

                return results.stream()
                                .map(row -> new TopSellingProductDTO(
                                                (Long) row[0],
                                                (String) row[1],
                                                (String) row[2],
                                                (Long) row[3]))
                                .toList();
        }

        public int getSoldQuantitySinceLastPurchase(
                        Long productId,
                        String token) {

                StockMovementDTO latestPurchase = productClient.getLatestPurchaseMovement(productId, token);

                if (latestPurchase == null) {
                        return 0;
                }

                LocalDateTime startDate = latestPurchase.getCreatedAt();

                List<SaleReportItem> sales = saleReportItemRepository
                                .findByProductIdAndCreatedAtGreaterThanEqual(
                                                productId,
                                                startDate);

                return sales.stream()
                                .mapToInt(SaleReportItem::getQuantity)
                                .sum();
        }

        public SupplierReportDTO getSupplierReport(
                        Long productId,
                        String token) {

                StockMovementDTO latestPurchase = productClient.getLatestPurchaseMovement(productId, token);

                ProductResponseDTO product = productClient.getProduct(productId, token);

                if (latestPurchase == null) {
                        return new SupplierReportDTO(
                                        productId,
                                        product.getName(),
                                        product.getBarcode(),
                                        null,
                                        0,
                                        0,
                                        0);
                }

                LocalDateTime startDate = latestPurchase.getCreatedAt();

                List<SaleReportItem> sales = saleReportItemRepository
                                .findByProductIdAndCreatedAtGreaterThanEqual(
                                                productId,
                                                startDate);

                int soldQuantity = sales.stream()
                                .mapToInt(SaleReportItem::getQuantity)
                                .sum();

                int suppliedQuantity = latestPurchase.getQuantity();

                int remainingQuantity = suppliedQuantity - soldQuantity;

                return new SupplierReportDTO(
                                productId,
                                product.getName(),
                                product.getBarcode(),
                                latestPurchase.getCreatedAt(),
                                suppliedQuantity,
                                soldQuantity,
                                remainingQuantity);
        }

        public List<SupplierReportDTO> getAllSupplierReports(String token) {

                List<ProductResponseDTO> products = productClient.getAllProducts(token);

                return products.stream()
                                .map(product -> getSupplierReport(product.getId(), token))
                                .toList();
        }
}