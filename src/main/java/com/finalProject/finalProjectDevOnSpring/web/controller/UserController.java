package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.services.user.UserService;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.BaseSearchCriteria;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User v1", description = "API для взаимодействия с контроллером управления пользователями")
public class UserController {
    private final UserService service;

    @Operation(
            summary = "Получить всех пользователей",
            description = "Вернет Page<UserDto> объект, принимает page - номер страницы, pageSize - размер страницы",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> findByCriteria(@Valid BaseSearchCriteria searchCriteria) {
        return ResponseEntity.ok(service.findAll(searchCriteria));
    }

    @Operation(
            summary = "Поиск пользователя по ID",
            description = "Вернет UserDto объект",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<UserDto> findById(@RequestParam Long userId) throws ApplicationNotFoundException {
        return ResponseEntity.ok(service.findById(userId));
    }

    @Operation(
            summary = "Поиск пользователя по userName",
            description = "Вернет UserDto объект",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/findByUsername")
    public ResponseEntity<UserDto> findByUsername(@RequestParam String userName) throws ApplicationNotFoundException {
        return ResponseEntity.ok(service.findByUsername(userName));
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "Вернет UserDto объект",
            tags = {"user"}
    )
    @PostMapping
    public ResponseEntity<UserDto> createNew(@RequestBody UserChangeRequest userChangeRequest) throws Exception {
        return ResponseEntity.ok(service.saveOrUpdate(null, userChangeRequest));
    }

    @Operation(
            summary = "Изменить пользователя",
            description = "Вернет UserDto объект. Наличие userId обязательно",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping
    public ResponseEntity<UserDto> edit(@RequestParam Long userId,
                                              @RequestBody UserChangeRequest userChangeRequest) throws Exception {
        if (Objects.isNull(userId)) {
            throw new RuntimeException("Parameter userId can't be null");
        }
        return ResponseEntity.ok(service.saveOrUpdate(userId, userChangeRequest));
    }

    @Operation(
            summary = "Удалить пользователя по Id",
            description = "Ничего не вернет",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long userId) {
        service.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
