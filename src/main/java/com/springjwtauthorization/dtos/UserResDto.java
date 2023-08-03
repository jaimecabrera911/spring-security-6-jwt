package com.springjwtauthorization.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.springjwtauthorization.entities.UserEntity}
 */
@Data
@Builder
public class UserResDto implements Serializable {
    private String username;
    private String email;
    private String phone;
    private Set<RoleResDto> roles;
}