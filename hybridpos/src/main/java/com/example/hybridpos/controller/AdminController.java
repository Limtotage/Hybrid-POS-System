package com.example.hybridpos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.RegisterCashierDTO;
import com.example.hybridpos.dto.UpdateCashierDTO;
import com.example.hybridpos.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    @PostMapping("/cashiers")
    public ResponseEntity<?> createCashier(
            @RequestBody RegisterCashierDTO dto) {

        return ResponseEntity.ok(
                userService.createCashier(dto)
        );
    }

    @DeleteMapping("/cashiers/{id}")
    public ResponseEntity<Void> deleteCashier(
            @PathVariable Long id) {

        userService.deleteCashier(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cashiers/{id}")
    public ResponseEntity<?> updateCashier(
            @PathVariable Long id,
            @RequestBody UpdateCashierDTO dto) {

        return ResponseEntity.ok(
                userService.updateCashier(id, dto)
        );
    }

    @GetMapping("/cashiers")
    public ResponseEntity<?> getCashiers() {

        return ResponseEntity.ok(
                userService.getAllCashiers()
        );
    }
}
