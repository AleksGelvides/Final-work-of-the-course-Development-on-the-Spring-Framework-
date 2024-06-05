package com.finalProject.finalProjectDevOnSpring.dto.booking;

import java.time.LocalDateTime;

public record BookingRequest(
        LocalDateTime bookingFrom,
        LocalDateTime bookingTo,
        Long userId,
        Long roomId,
        String comment
) {
}
