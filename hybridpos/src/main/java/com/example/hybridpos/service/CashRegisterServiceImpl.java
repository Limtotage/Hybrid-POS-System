package com.example.hybridpos.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;
import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.repository.CashRegisterRepository;
import com.example.hybridpos.repository.ShopRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {
    private final CashRegisterRepository cashRegisterRepository;
    private final ShopRepository shopRepository;

    @Override
    public CashRegister openCash(Long shopId, CashOpenDTO dto) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow();

        CashRegister cash = new CashRegister();
        cash.setShop(shop);
        cash.setOpeningCash(dto.getOpeningCash());
        cash.setOpenedAt(LocalDateTime.now());
        cash.setOpen(true);

        return cashRegisterRepository.save(cash);
    }

    @Override
    public CashRegister closeCash(Long cashId, CashCloseDTO dto) {

        CashRegister cash = cashRegisterRepository.findById(cashId)
                .orElseThrow();

        cash.setClosingCash(dto.getClosingCash());
        cash.setClosedAt(LocalDateTime.now());
        cash.setOpen(false);

        return cashRegisterRepository.save(cash);
    }
}
