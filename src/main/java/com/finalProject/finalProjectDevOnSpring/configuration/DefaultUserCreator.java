package com.finalProject.finalProjectDevOnSpring.configuration;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.services.user.UserService;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserChangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "initUser", havingValue = "true")
public class DefaultUserCreator implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        try {
            checkAndInitAdmin();
            checkAndInitUser();
        } catch (Exception e) {
            throw new RuntimeException("Приложение не запустилось: " + e.getMessage());
        }
    }

    private void checkAndInitAdmin() throws ApplicationException, ApplicationNotFoundException {
        try {
            userService.findById(1L);
        } catch (ApplicationNotFoundException e) {
            userService.saveOrUpdate(null,
                    new UserChangeRequest(
                            "admin",
                            "admin@gmail.com",
                            "admin",
                            Set.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN)));

        }
    }

    private void checkAndInitUser() throws ApplicationException, ApplicationNotFoundException {
        try {
            userService.findById(2L);
        } catch (ApplicationNotFoundException e) {
            userService.saveOrUpdate(null,
                    new UserChangeRequest(
                            "user",
                            "user@gmail.com",
                            "user",
                            Set.of(RoleType.ROLE_USER)));

        }
    }
}
