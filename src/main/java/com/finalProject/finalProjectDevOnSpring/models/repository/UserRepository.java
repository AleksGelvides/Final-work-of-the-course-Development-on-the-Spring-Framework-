package com.finalProject.finalProjectDevOnSpring.models.repository;

import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsernameAndEmail(String username, String email);
}
