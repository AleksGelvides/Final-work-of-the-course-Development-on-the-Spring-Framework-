package com.finalProject.finalProjectDevOnSpring.web.dto.room;

import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelDto;

import java.math.BigDecimal;

public record RoomDto(
        Long id,
        String roomName,
        String roomDescription,
        Integer roomNumber,
        Integer maximumCapacity,
        BigDecimal roomPrice,
        HotelDto hotel
) {
}
