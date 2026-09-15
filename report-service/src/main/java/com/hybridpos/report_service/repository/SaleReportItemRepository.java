package com.hybridpos.report_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridpos.report_service.entity.SaleReportItem;

public interface SaleReportItemRepository extends JpaRepository<SaleReportItem, Long> {

    List<SaleReportItem> findByProductId(Long productId);

    List<SaleReportItem> findBySaleId(Long saleId);

    List<SaleReportItem> findByProductIdAndCreatedAtGreaterThanEqual(
            Long productId,
            LocalDateTime startDate);
}