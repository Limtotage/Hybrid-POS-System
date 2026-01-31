package com.example.hybridpos.service;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;

public interface CashRegisterService {
    CashRegister openCash(Long shopId, CashOpenDTO dto);

    CashRegister closeCash(Long cashId, CashCloseDTO dto);
}
