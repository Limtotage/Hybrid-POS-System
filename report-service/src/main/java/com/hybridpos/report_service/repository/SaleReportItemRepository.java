package com.hybridpos.report_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridpos.report_service.entity.SaleReportItem;

public interface SaleReportItemRepository extends JpaRepository<SaleReportItem, Long> {

    List<SaleReportItem> findByProductIdAndCreatedAtGreaterThanEqual(
            Long productId,
            LocalDateTime startDate);

    @Query("""
                SELECT
                    s.productId,
                    s.productName,
                    s.barcode,
                    SUM(s.quantity),
                    SUM(s.priceAtSale * s.quantity)
                FROM SaleReportItem s
                WHERE s.createdAt >= :start
                  AND s.createdAt < :end
                GROUP BY s.productId, s.productName, s.barcode
            """)
    List<Object[]> getProductSalesBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("""
                SELECT
                    s.productId,
                    s.productName,
                    s.barcode,
                    SUM(s.quantity)
                FROM SaleReportItem s
                WHERE s.createdAt >= :start
                  AND s.createdAt < :end
                GROUP BY s.productId, s.productName, s.barcode
                ORDER BY SUM(s.quantity) DESC
            """)
    List<Object[]> getTopSellingProducts(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("""
                SELECT
                    s.productId,
                    s.productName,
                    s.barcode,
                    SUM(s.quantity)
                FROM SaleReportItem s
                GROUP BY s.productId, s.productName, s.barcode
                ORDER BY SUM(s.quantity) DESC
            """)
    List<Object[]> getTopSellingProductsAllTime();
}