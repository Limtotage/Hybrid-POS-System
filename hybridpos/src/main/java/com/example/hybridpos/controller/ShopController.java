package com.example.hybridpos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.ShopDTO;
import com.example.hybridpos.interfaces.ShopInterface;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopInterface shopService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public ResponseEntity<List<ShopDTO>> getShops() {
        return ResponseEntity.ok(shopService.getAll());
    }
}

