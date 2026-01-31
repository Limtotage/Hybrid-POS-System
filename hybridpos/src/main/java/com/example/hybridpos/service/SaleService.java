package com.example.hybridpos.service;

import java.time.LocalDateTime;

import com.example.hybridpos.dto.SaleCreateDTO;
import com.example.hybridpos.dto.SaleResponseDTO;

public interface SaleService {
    SaleResponseDTO makeSale(Long cashId, SaleCreateDTO dto);
    SaleResponseDTO getReport(LocalDateTime startDate, LocalDateTime endDate);
}
