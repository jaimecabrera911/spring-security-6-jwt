package com.springjwtauthorization.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthRequest {
    @NotNull
    @Email
    @Size(min = 5, max = 50)
    private String username;

    @NotNull
    @Size(min = 5, max = 10)
    private String password;
}