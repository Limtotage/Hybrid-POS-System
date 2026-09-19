package com.hybridPOS.auth_service.dto;

import lombok.Data;

@Data
public class UpdateAdminResponseDTO {

    private Long id;
    private String username;
    private String role;
    private boolean enabled;
    private String token;
}