package com.hybridpos.report_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hybridpos.report_service.client.ProductClient;
import com.hybridpos.report_service.dto.ProductResponseDTO;
import com.hybridpos.report_service.dto.StockMovementDTO;
import com.hybridpos.report_service.dto.SupplierReportDTO;
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
}