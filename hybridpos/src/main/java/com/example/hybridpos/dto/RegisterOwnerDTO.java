package com.example.hybridpos.dto;

import lombok.Data;

@Data
public class RegisterOwnerDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
