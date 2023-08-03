package com.springjwtauthorization.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String username;
    private String accessToken;
}