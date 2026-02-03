package com.example.hybridpos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.CashCloseDTO;
import com.example.hybridpos.dto.CashOpenDTO;
import com.example.hybridpos.entity.CashRegister;
import com.example.hybridpos.service.CashRegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cash")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CASHIER')")
public class CashRegisterController {

    private final CashRegisterService cashService;

    @PostMapping("/open/{shopId}")
    public CashRegister open(
            @PathVariable Long shopId,
            @RequestBody CashOpenDTO dto,
            Authentication auth) {
        return cashService.openCash(shopId, dto);
    }

    @PostMapping("/close/{cashId}")
    public CashRegister close(
            @PathVariable Long cashId,
            @RequestBody CashCloseDTO dto) {
        return cashService.closeCash(cashId, dto);
    }
}
