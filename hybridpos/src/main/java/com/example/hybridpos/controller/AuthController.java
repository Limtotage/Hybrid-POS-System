package com.example.hybridpos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.AuthRequest;
import com.example.hybridpos.dto.AuthResponse;
import com.example.hybridpos.jwtsystem.JwtUtil;
import com.example.hybridpos.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                authService.loadUserByUsername(request.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
    @PostMapping("/register-owner")
    public ResponseEntity<?> RegisterOwner(@RequestBody AuthRequest request) {
        authService.createOwner(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Owner created");
    }
    @PostMapping("/create-cashier")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> RegisterCashier(@RequestBody AuthRequest request) {
        authService.createCashier(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Cashier created");
    }
}

