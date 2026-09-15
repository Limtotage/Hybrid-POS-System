package com.hybridpos.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hybridpos.product_service.entity.StockMovement;
import com.hybridpos.product_service.enums.StockMovementType;
import com.hybridpos.product_service.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public List<StockMovement> getPurchaseMovements(Long productId) {
        return stockMovementRepository.findByProductIdAndType(
                productId,
                StockMovementType.PURCHASE
        );
    }
}