package com.example.hybridpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.ShopCreateDTO;
import com.example.hybridpos.dto.ShopDTO;
import com.example.hybridpos.service.ShopService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public ResponseEntity<List<ShopDTO>> getShops() {
        return ResponseEntity.ok(shopService.getAll());
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/create")
    public ResponseEntity<?> createShop(@RequestBody ShopCreateDTO dto) {
        shopService.createShop(dto);
        return ResponseEntity.ok(Map.of("message", "Shop created."));
    }
}

