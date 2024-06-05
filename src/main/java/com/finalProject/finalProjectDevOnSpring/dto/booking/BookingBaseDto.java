package com.finalProject.finalProjectDevOnSpring.dto.booking;

import com.finalProject.finalProjectDevOnSpring.dto.room.RoomDto;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserDto;

import java.time.LocalDateTime;

public record BookingBaseDto(
        LocalDateTime bookingFrom,
        LocalDateTime bookingTo,
        UserDto user,
        RoomDto room,
        String comment
) {
}
