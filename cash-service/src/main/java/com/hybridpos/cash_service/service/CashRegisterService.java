package com.hybridpos.cash_service.service;

import java.util.List;

import com.hybridpos.cash_service.dto.CashRegisterReportDTO;
import com.hybridpos.cash_service.dto.CashSaleDTO;
import com.hybridpos.cash_service.entity.CashRegister;

public interface CashRegisterService {

    CashRegister createCashRegister(String name);

    List<CashRegister> getAllCashRegisters();

    CashRegister getCashRegister(Long id);

    void deleteCashRegister(Long id);
    void processSale(Long cashRegisterId, CashSaleDTO dto);
    void closeCashRegister(Long id);
    void openCashRegister(Long id);
    boolean validateSale(Long cashRegisterId);
    CashRegisterReportDTO getCashRegisterReport(Long id);
}
