package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.services.statistic.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService service;

    @Operation(
            summary = "Получить csv файл с регистрациями пользователей",
            description = "Вернет statistics.csv файл",
            tags = {"statistic"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/userRegistration")
    public void getUserRegistrationStatistic(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"statistics.csv\"");
        service.getStatisticRegistrationEvent(response);
    }

    @Operation(
            summary = "Получить csv файл с бронированием комнат",
            description = "Вернет statistics.csv файл",
            tags = {"statistic"}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/booking")
    public void getBookingStatistic(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"statistics.csv\"");
        service.getStatisticBookingEvent(response);
    }
}
