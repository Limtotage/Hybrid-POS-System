package com.hybridpos.sale_service.service;

import java.time.LocalDateTime;

import com.hybridpos.sale_service.dto.SaleCreateDTO;
import com.hybridpos.sale_service.dto.SaleResponseDTO;

public interface SaleService {

    SaleResponseDTO makeSale(
            Long cashId,
            SaleCreateDTO dto,
            String token
    );

    SaleResponseDTO getReport(
            LocalDateTime start,
            LocalDateTime end
    );
}