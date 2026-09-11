package com.hybridpos.sale_service.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridpos.sale_service.dto.SaleCreateDTO;
import com.hybridpos.sale_service.dto.SaleResponseDTO;
import com.hybridpos.sale_service.service.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PreAuthorize("hasRole('CASHIER')")
    @PostMapping("/{cashId}")
    public ResponseEntity<SaleResponseDTO> makeSale(
            @PathVariable Long cashId,
            @RequestBody SaleCreateDTO dto,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);

        return ResponseEntity.ok(
                saleService.makeSale(
                        cashId,
                        dto,
                        token
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/report")
    public ResponseEntity<SaleResponseDTO> getReport(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {

        return ResponseEntity.ok(
                saleService.getReport(start, end)
        );
    }
}