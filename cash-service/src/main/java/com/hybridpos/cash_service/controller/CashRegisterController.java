package com.hybridpos.cash_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridpos.cash_service.dto.CashRegisterReportDTO;
import com.hybridpos.cash_service.dto.CashSaleDTO;
import com.hybridpos.cash_service.entity.CashRegister;
import com.hybridpos.cash_service.service.CashRegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cash-registers")
@RequiredArgsConstructor
public class CashRegisterController {

    private final CashRegisterService cashRegisterService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CashRegister> createCashRegister(
            @RequestParam String name) {

        return ResponseEntity.ok(
                cashRegisterService.createCashRegister(name));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN' ,'CASHIER')")
    public ResponseEntity<List<CashRegister>> getAllCashRegisters() {

        return ResponseEntity.ok(
                cashRegisterService.getAllCashRegisters());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN' ,'CASHIER')")
    public ResponseEntity<CashRegister> getCashRegister(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cashRegisterService.getCashRegister(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCashRegister(
            @PathVariable Long id) {

        cashRegisterService.deleteCashRegister(id);

        return ResponseEntity.noContent().build();
    }
    

    @PostMapping("/{id}/sale")
    @PreAuthorize("hasAnyRole('ADMIN' ,'CASHIER')")
    public ResponseEntity<Void> processSale(
            @PathVariable Long id,
            @RequestBody CashSaleDTO dto) {

        System.out.println("========== CASH CONTROLLER ==========");
        System.out.println("CASH ID: " + id);
        System.out.println("CASH PAID: " + dto.getCashPaid());
        System.out.println("CARD PAID: " + dto.getCardPaid());

        cashRegisterService.processSale(id, dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/close")
    @PreAuthorize("hasAnyRole('CASHIER')")
    public ResponseEntity<Void> closeCashRegister(
            @PathVariable Long id) {

        cashRegisterService.closeCashRegister(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/open")
    @PreAuthorize("hasAnyRole('CASHIER')")

    public ResponseEntity<Void> openCashRegister(
            @PathVariable Long id) {

        cashRegisterService.openCashRegister(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/validate-sale")
    @PreAuthorize("hasAnyRole('ADMIN' ,'CASHIER')")

    public ResponseEntity<Boolean> validateSale(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cashRegisterService.validateSale(id));
    }

    @GetMapping("/{id}/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CashRegisterReportDTO> getCashRegisterReport(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cashRegisterService.getCashRegisterReport(id));
    }
}