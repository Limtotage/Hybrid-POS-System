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
public class CashRegisterController {

    private final CashRegisterService cashService;

    @PreAuthorize("hasRole('CASHIER')")
    @PostMapping("/open")
    public CashRegister open(
            @RequestBody CashOpenDTO dto,
            Authentication auth) {

        return cashService.openCash(dto, auth);
    }

    @PreAuthorize("hasRole('CASHIER')")
    @PostMapping("/close/{cashId}")
    public CashRegister close(
            @PathVariable Long cashId,
            @RequestBody CashCloseDTO dto,
            Authentication auth) {

        return cashService.closeCash(
                cashId,
                dto,
                auth
        );
    }
}
