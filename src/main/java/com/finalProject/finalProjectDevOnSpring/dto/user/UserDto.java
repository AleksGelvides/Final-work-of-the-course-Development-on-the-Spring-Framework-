package com.finalProject.finalProjectDevOnSpring.dto.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        String email,
        Set<RoleType> role
) {
}
