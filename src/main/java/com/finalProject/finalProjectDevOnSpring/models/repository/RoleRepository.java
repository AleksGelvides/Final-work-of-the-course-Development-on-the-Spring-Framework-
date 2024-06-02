package com.finalProject.finalProjectDevOnSpring.models.repository;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.UserRole;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<UserRole, Long> {
    Optional<UserRole> findByRole(RoleType roleType);

}
