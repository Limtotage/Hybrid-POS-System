package com.example.hybridpos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.ProductResponseDTO;
import com.example.hybridpos.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products/view")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('OWNER','CASHIER')")
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/{barcode}")
    public ProductResponseDTO getByBarcode(@PathVariable String barcode) {
        return productService.getByBarcode(barcode);
    }
}

