package com.personal.auth_service.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private final String token;
}