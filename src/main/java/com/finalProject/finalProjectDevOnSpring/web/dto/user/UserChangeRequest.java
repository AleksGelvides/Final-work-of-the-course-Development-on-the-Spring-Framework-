package com.finalProject.finalProjectDevOnSpring.web.dto.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;

import java.util.Set;
public record UserChangeRequest(
        String username,
        String email,
        String password,
        Set<RoleType> role
        ) {
}
