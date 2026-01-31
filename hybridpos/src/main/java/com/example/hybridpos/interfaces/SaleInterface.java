package com.example.hybridpos.interfaces;

import com.example.hybridpos.dto.SaleCreateDTO;

public interface SaleInterface {
    void makeSale(Long cashId, SaleCreateDTO dto);
}
