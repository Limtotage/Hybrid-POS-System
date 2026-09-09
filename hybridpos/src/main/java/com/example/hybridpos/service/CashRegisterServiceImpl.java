package com.example.hybridpos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;
import com.example.hybridpos.entity.MyUser;
import com.example.hybridpos.repository.CashRegisterRepository;
import com.example.hybridpos.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepository cashRegisterRepository;
    private final UserRepository userRepo;

    @Override
    public CashRegister openCash(
            CashOpenDTO dto,
            Authentication user) {

        MyUser cashier = userRepo.findByUsername(user.getName())
                .orElseThrow(() -> new RuntimeException("Cashier bulunamadı"));


        if (cashRegisterRepository.existsByCashierAndOpenTrue(cashier)) {
            throw new RuntimeException("Cashier already has an open register");
        }

        CashRegister cash = new CashRegister();

        cash.setOpeningCash(dto.getOpeningCash());
        cash.setOpen(true);
        cash.setOpenedAt(LocalDateTime.now());
        cash.setCashier(cashier);

        cash.setTotalCashSales(BigDecimal.ZERO);
        cash.setTotalCardSales(BigDecimal.ZERO);

        return cashRegisterRepository.save(cash);
    }

    @Override
    public CashRegister closeCash(
            Long cashId,
            CashCloseDTO dto,
            Authentication user) {

        MyUser cashier = userRepo.findByUsername(user.getName())
                .orElseThrow(() -> new RuntimeException("Cashier bulunamadı"));

        CashRegister cash = cashRegisterRepository.findById(cashId)
                .orElseThrow(() -> new RuntimeException("Kasa bulunamadı"));

        if (!cash.isOpen()) {
            throw new RuntimeException("This cash register is already closed");
        }

        if (!cash.getCashier().getId().equals(cashier.getId())) {
            throw new RuntimeException("Bu kasa size ait değil");
        }

        cash.setClosingCash(dto.getClosingCash());
        cash.setClosedAt(LocalDateTime.now());
        cash.setOpen(false);

        return cashRegisterRepository.save(cash);
    }

    @Override
    public CashRegister getMyOpenCash(Authentication user) {

        MyUser cashier = userRepo.findByUsername(user.getName())
                .orElseThrow(() -> new RuntimeException("Cashier bulunamadı"));

        return cashRegisterRepository.findByCashierAndOpenTrue(cashier)
                .orElseThrow(() -> new RuntimeException("Açık kasa bulunamadı"));
    }
}