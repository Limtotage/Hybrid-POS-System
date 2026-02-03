package com.example.hybridpos.service;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;

public interface CashRegisterService {
    CashRegister openCash(long shopId, CashOpenDTO dto);

    CashRegister closeCash(long shopId, CashCloseDTO dto);
}
