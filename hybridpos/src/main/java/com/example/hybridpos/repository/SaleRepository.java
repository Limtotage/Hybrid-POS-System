package com.example.hybridpos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hybridpos.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCashRegisterId(Long cashRegisterId);

    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT SUM(s.totalPrice)
                FROM Sale s
            """)
    Double sumTotalSales();

    // Günlük toplam ciro
    @Query("""
                SELECT SUM(s.totalPrice)
                FROM Sale s
                WHERE s.saleDate BETWEEN :start AND :end
            """)
    Double getTotalRevenue(LocalDateTime start, LocalDateTime end);

    // En çok satan ürün
    @Query("""
                SELECT si.product.id, SUM(si.quantity)
                FROM SaleItem si
                GROUP BY si.product.id
                ORDER BY SUM(si.quantity) DESC
            """)
    List<Object[]> findTopSellingProducts();
}
