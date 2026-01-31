package com.example.hybridpos.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.AuthResponseDTO;
import com.example.hybridpos.dto.LoginDTO;
import com.example.hybridpos.dto.RegisterCashierDTO;
import com.example.hybridpos.dto.RegisterOwnerDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-owner")
    public void registerOwner(@RequestBody RegisterOwnerDTO dto) {
        authService.registerOwner(dto);
    }

    @PostMapping("/register-cashier")
    @PreAuthorize("hasRole('OWNER')")
    public void registerCashier(@RequestBody RegisterCashierDTO dto) {
        authService.registerCashier(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginDTO dto) {
        return authService.login(dto);
    }
}

