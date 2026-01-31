package com.example.hybridpos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.entity.WholesalerList;
import com.example.hybridpos.service.WholesalerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wholesaler")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class WholesalerController {

    private final WholesalerService wholesalerService;

    @PostMapping("/create/{shopId}")
    public WholesalerList create(@PathVariable Long shopId) {
        return wholesalerService.createList(shopId);
    }
}
