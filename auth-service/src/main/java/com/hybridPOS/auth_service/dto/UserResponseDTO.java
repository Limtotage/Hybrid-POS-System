package com.hybridPOS.auth_service.dto;

import com.hybridPOS.auth_service.enums.Role;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private Role role;
    private boolean enabled;
}