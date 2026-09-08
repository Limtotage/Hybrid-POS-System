package com.example.hybridpos.service;

import org.springframework.security.core.Authentication;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;

public interface CashRegisterService {
    CashRegister openCash(CashOpenDTO dto, Authentication user);

    CashRegister closeCash(long cashId, CashCloseDTO dto,Authentication user);
}
