package com.example.hybridpos.service;

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
    private final CashRegisterRepository cashRepo;

    @Override
    public CashRegister openCash(
            CashOpenDTO dto, Authentication user){

        if (cashRepo.existsByNameAndOpenTrue(dto.getCashName())) {
            throw new RuntimeException("This cash register is already open");
        }

        MyUser cashier = userRepo.findByUsername(user.getName()).orElseThrow();
        if (cashRepo.existsByCashierAndOpenTrue(cashier)) {
            throw new RuntimeException("Cashier already has an open register");
        }

        CashRegister cash = new CashRegister();
        cash.setName(dto.getCashName());
        cash.setOpeningCash(dto.getOpeningCash());
        cash.setOpen(true);
        cash.setOpenedAt(LocalDateTime.now());
        cash.setCashier(cashier);

        return cashRepo.save(cash);
    }

    @Override
    public CashRegister closeCash(long cashId, CashCloseDTO dto, Authentication user) {
        if (!cashRepo.existsByNameAndOpenTrue(dto.getCashName())) {
            throw new RuntimeException("This cash register is already closed");
        }

        MyUser cashier = userRepo.findByUsername(user.getName()).orElseThrow();

        CashRegister cash = cashRegisterRepository.findById(cashId)
                .orElseThrow();

        cash.setClosingCash(dto.getClosingCash());
        cash.setName(dto.getCashName());
        cash.setClosedAt(LocalDateTime.now());
        cash.setCashier(cashier);
        cash.setOpen(false);

        return cashRegisterRepository.save(cash);
    }
}
