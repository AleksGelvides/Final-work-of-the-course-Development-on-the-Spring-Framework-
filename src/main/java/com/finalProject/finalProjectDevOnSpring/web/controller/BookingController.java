package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.services.booking.BookingService;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingBaseDto;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingRequest;
import com.finalProject.finalProjectDevOnSpring.dto.search.BookingSearchCriteria;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@Tag(name = "Booking v1", description = "API для взаимодействия с контроллером бронирования")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public BookingBaseDto booking(@RequestBody BookingRequest request) throws ApplicationException, ApplicationNotFoundException {
        return bookingService.bookRoom(request);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<BookingBaseDto> getAllByUserId(BookingSearchCriteria criteria) {
        return bookingService.findAll(criteria);
    }
}
