package com.hybridpos.product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridpos.product_service.entity.StockMovement;
import com.hybridpos.product_service.enums.StockMovementType;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    
    List<StockMovement> findByProductId(Long productId);

    List<StockMovement> findByProductIdAndType(
        Long productId,
        StockMovementType type
    );
}
