package com.example.hybridpos.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.SaleCreateDTO;
import com.example.hybridpos.dto.SaleResponseDTO;
import com.example.hybridpos.service.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    // CASHIER: satış yap
    @PreAuthorize("hasRole('CASHIER')")
    @PostMapping("/{cashId}")
    public ResponseEntity<SaleResponseDTO> makeSale(@PathVariable Long cashId, @RequestBody SaleCreateDTO dto) {
        return ResponseEntity.ok(saleService.makeSale(cashId,dto));
    }

    // OWNER: satış raporları
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/report")
    public ResponseEntity<SaleResponseDTO> getReport(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(saleService.getReport(start, end));
    }
}

