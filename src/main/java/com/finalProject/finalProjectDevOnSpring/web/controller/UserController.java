package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.services.user.UserService;
import com.finalProject.finalProjectDevOnSpring.dto.search.BaseSearchCriteria;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public Page<UserDto> findByCriteria(@Valid BaseSearchCriteria searchCriteria) {
        return service.findAll(searchCriteria);
    }

    @Operation(
            summary = "Поиск пользователя по ID",
            description = "Вернет UserDto объект",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public UserDto findById(@RequestParam Long userId) throws ApplicationNotFoundException {
        return service.findById(userId);
    }

    @Operation(
            summary = "Поиск пользователя по userName",
            description = "Вернет UserDto объект",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/findByUsername")
    public UserDto findByUsername(@RequestParam String userName) throws ApplicationNotFoundException {
        return service.findByUsername(userName);
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "Вернет UserDto объект",
            tags = {"user"}
    )
    @PostMapping
    public UserDto createNew(@RequestBody UserChangeRequest userChangeRequest) throws Exception {
        return service.saveOrUpdate(null, userChangeRequest);
    }

    @Operation(
            summary = "Изменить пользователя",
            description = "Вернет UserDto объект. Наличие userId обязательно",
            tags = {"user"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping
    public UserDto edit(@RequestParam Long userId,
                        @RequestBody UserChangeRequest userChangeRequest) throws Exception {
        return service.saveOrUpdate(userId, userChangeRequest);
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
