package com.finalProject.finalProjectDevOnSpring.web.dto.booking;

import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomDto;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserDto;

import java.time.LocalDateTime;

public record BookingBaseDto(
        LocalDateTime bookingFrom,
        LocalDateTime bookingTo,
        UserDto user,
        RoomDto room,
        String comment
) {
}
