package com.hybridpos.report_service.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hybridpos.report_service.entity.SaleReport;

public interface SaleReportRepository extends JpaRepository<SaleReport, Long> {
    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM SaleReport s")
    BigDecimal getTotalRevenue();

    @Query("SELECT COALESCE(SUM(s.cashPaid), 0) FROM SaleReport s")
    BigDecimal getTotalCash();

    @Query("SELECT COALESCE(SUM(s.cardPaid), 0) FROM SaleReport s")
    BigDecimal getTotalCard();

    @Query("""
                SELECT COUNT(s)
                FROM SaleReport s
                WHERE s.createdAt >= :start
                AND s.createdAt < :end
            """)
    Long countSalesBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT COALESCE(SUM(s.totalAmount), 0)
                FROM SaleReport s
                WHERE s.createdAt >= :start
                AND s.createdAt < :end
            """)
    BigDecimal getRevenueBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT COALESCE(SUM(s.cashPaid), 0)
                FROM SaleReport s
                WHERE s.createdAt >= :start
                AND s.createdAt < :end
            """)
    BigDecimal getCashBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT COALESCE(SUM(s.cardPaid), 0)
                FROM SaleReport s
                WHERE s.createdAt >= :start
                AND s.createdAt < :end
            """)
    BigDecimal getCardBetween(LocalDateTime start, LocalDateTime end);
    boolean existsBySaleId(Long saleId);
}