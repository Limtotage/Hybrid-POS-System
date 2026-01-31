package com.example.hybridpos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hybridpos.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCashRegisterId(Long cashRegisterId);

    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    // Günlük toplam ciro
    @Query("""
        SELECT SUM(s.totalPrice)
        FROM Sale s
        WHERE s.saleDate BETWEEN :start AND :end
    """)
    Double getTotalRevenue(LocalDateTime start, LocalDateTime end);

    // En çok satan ürün
    @Query("""
        SELECT s.product.id, SUM(s.quantity)
        FROM Sale s
        GROUP BY s.product.id
        ORDER BY SUM(s.quantity) DESC
    """)
    List<Object[]> findTopSellingProducts();
}
