package com.example.hybridpos.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;
import com.example.hybridpos.entity.MyUser;
import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.repository.CashRegisterRepository;
import com.example.hybridpos.repository.ShopRepository;
import com.example.hybridpos.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepository cashRegisterRepository;
    private final UserRepository userRepo;
    private final CashRegisterRepository cashRepo;
    private final ShopRepository shopRepo;

    @Override
    public CashRegister openCash(
            long shopId, CashOpenDTO dto) {

        if (cashRepo.existsByNameAndOpenTrue(dto.getCashName())) {
            throw new RuntimeException("This cash register is already open");
        }

        MyUser cashier = userRepo.findByUsername(dto.getUsername()).orElseThrow();
        if (cashRepo.existsByCashierAndOpenTrue(cashier)) {
            throw new RuntimeException("Cashier already has an open register");
        }

        Shop shop = shopRepo.findById(shopId).orElseThrow();

        CashRegister cash = new CashRegister();
        cash.setName(dto.getCashName());
        cash.setOpeningCash(dto.getOpeningCash());
        cash.setOpen(true);
        cash.setOpenedAt(LocalDateTime.now());
        cash.setCashier(cashier);
        cash.setShop(shop);

        return cashRepo.save(cash);
    }

    @Override
    public CashRegister closeCash(long cashId, CashCloseDTO dto) {
        if (!cashRepo.existsByNameAndOpenTrue(dto.getCashName())) {
            throw new RuntimeException("This cash register is already closed");
        }

        MyUser cashier = userRepo.findByUsername(dto.getUsername()).orElseThrow();

        CashRegister cash = cashRegisterRepository.findById(cashId)
                .orElseThrow();

        cash.setClosingCash(dto.getClosingCash());
        cash.setName(dto.getCashName());
        cash.setClosedAt(LocalDateTime.now());
        cash.setCashier(cashier);
        cash.setShop(cash.getShop());
        cash.setOpen(false);

        return cashRegisterRepository.save(cash);
    }
}
