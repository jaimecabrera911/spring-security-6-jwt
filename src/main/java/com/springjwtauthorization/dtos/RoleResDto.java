package com.springjwtauthorization.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.springjwtauthorization.entities.RoleEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResDto implements Serializable {
    private String name;
}