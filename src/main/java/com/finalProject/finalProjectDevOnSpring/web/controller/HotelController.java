package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.services.hotel.HotelService;
import com.finalProject.finalProjectDevOnSpring.dto.hotel.HotelChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.hotel.HotelDto;
import com.finalProject.finalProjectDevOnSpring.dto.hotel.HotelRate;
import com.finalProject.finalProjectDevOnSpring.dto.search.HotelSearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Hotel v1", description = "API для взаимодействия с контроллером управления отелями")
public class HotelController {
    private final HotelService service;

    @Operation(
            summary = "Получить все отели",
            description = "Вернет Page<HotelDto> объект, принимает page - номер страницы, pageSize - размер страницы",
            tags = {"hotel"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/all")
    public Page<HotelDto> findByCriteria(@Valid HotelSearchCriteria hotelSearchCriteria) {
        return service.findAll(hotelSearchCriteria);
    }

    @Operation(
            summary = "Поиск отеля по ID",
            description = "Вернет HotelDto объект",
            tags = {"hotel"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public HotelDto findById(@RequestParam Long hotelId) throws ApplicationNotFoundException {
        return service.findById(hotelId);
    }

    @Operation(
            summary = "Поставить оценку отелю",
            description = "Вернет HotelDto объект",
            tags = {"hotel"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/rate")
    public HotelDto rate(@RequestBody HotelRate rate) throws ApplicationNotFoundException {
        return service.rate(rate);
    }

    @Operation(
            summary = "Создать новый отель",
            description = "Вернет HotelDto объект",
            tags = {"hotel"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public HotelDto createNew(@RequestBody HotelChangeRequest hotelChangeRequest) throws ApplicationNotFoundException, ApplicationException {
        return service.saveOrUpdate(null, hotelChangeRequest);
    }

    @Operation(
            summary = "Изменить отель",
            description = "Вернет HotelDto объект. Наличие hotelId обязательно",
            tags = {"hotel"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping
    public HotelDto edit(@RequestParam Long hotelId,
                         @RequestBody HotelChangeRequest hotelChangeRequest) throws ApplicationNotFoundException, ApplicationException {
        return service.saveOrUpdate(hotelId, hotelChangeRequest);
    }

    @Operation(
            summary = "Удалить отель по Id",
            description = "Ничего не вернет",
            tags = {"hotel"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long hotelId) {
        service.deleteById(hotelId);
        return ResponseEntity.noContent().build();
    }
}
