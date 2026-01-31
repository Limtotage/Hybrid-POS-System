package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String role;
}
