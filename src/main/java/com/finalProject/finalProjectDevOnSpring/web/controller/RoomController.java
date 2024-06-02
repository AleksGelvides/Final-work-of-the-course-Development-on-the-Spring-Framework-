package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.services.hotel.RoomService;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomDto;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.RoomSearchCriteria;
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
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
@Tag(name = "Room v1", description = "API для взаимодействия с контроллером управления комнатами")
public class RoomController {
    private final RoomService service;

    @Operation(
            summary = "Получить все комнаты",
            description = "Вернет Page<RoomDto> объект, принимает page - номер страницы, pageSize - размер страницы, roomId - id отеля (ОБЯЗАТЕЛЕН)",
            tags = {"room"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<Page<RoomDto>> findByCriteria(@Valid RoomSearchCriteria hotelSearchCriteria) {
        return ResponseEntity.ok(service.findAll(hotelSearchCriteria));
    }

    @Operation(
            summary = "Поиск комнаты по ID",
            description = "Вернет RoomDto объект",
            tags = {"room"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<RoomDto> findById(@RequestParam Long roomId) throws ApplicationNotFoundException {
        return ResponseEntity.ok(service.findById(roomId));
    }

    @Operation(
            summary = "Создать новую комнату",
            description = "Вернет RoomDto объект",
            tags = {"room"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<RoomDto> createNew(@RequestBody @Valid RoomChangeRequest roomChangeRequest) throws ApplicationNotFoundException, ApplicationException {
        return ResponseEntity.ok(service.saveOrUpdate(null, roomChangeRequest));
    }

    @Operation(
            summary = "Изменить комнату",
            description = "Вернет RoomDto объект. Наличие roomId обязательно",
            tags = {"room"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<RoomDto> edit(@RequestParam Long roomId,
                                              @RequestBody RoomChangeRequest roomChangeRequest) throws ApplicationNotFoundException, ApplicationException {
        if (Objects.isNull(roomId)) {
            throw new RuntimeException("Parameter roomId can't be null");
        }
        return ResponseEntity.ok(service.saveOrUpdate(roomId, roomChangeRequest));
    }

    @Operation(
            summary = "Удалить комнату по Id",
            description = "Ничего не вернет",
            tags = {"room"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long roomId) {
        service.deleteById(roomId);
        return ResponseEntity.noContent().build();
    }
}
