package com.hybridPOS.auth_service.dto;


import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    public AuthResponse(String jwtToken) {
        this.token = jwtToken;
    }
}