package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    public AuthResponse(String jwtToken) {
        this.token = jwtToken;
    }
    public String getToken() {
        return token;
    }
}
