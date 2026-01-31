package com.example.hybridpos.interfaces;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;

public interface CashRegisterInterface {
    CashRegister openCash(Long shopId, CashOpenDTO dto);

    CashRegister closeCash(Long cashId, CashCloseDTO dto);
}
