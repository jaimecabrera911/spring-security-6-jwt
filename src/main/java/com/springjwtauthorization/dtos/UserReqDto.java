package com.springjwtauthorization.dtos;

import com.springjwtauthorization.entities.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Data
@Builder
public class UserReqDto implements Serializable {
    private String username;
    private String password;
    private String email;
    private String phone;
}