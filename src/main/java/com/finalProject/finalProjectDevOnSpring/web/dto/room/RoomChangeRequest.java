package com.finalProject.finalProjectDevOnSpring.web.dto.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RoomChangeRequest(
        @NotEmpty(message = "Необходимо указать название комнаты (При создании)")
        String roomName,
        @NotEmpty(message = "Необходимо указать описание комнаты (При создании)")
        String roomDescription,
        @NotNull(message = "Необходимо указать номер комнаты (При создании)")
        Integer roomNumber,
        @NotNull(message = "Необходимо указать, сколько человек вмещает номер (При создании)")
        Integer maximumCapacity,
        @NotNull(message = "hotelId не может быть пустым")
        @Min(value = 1, message = "ID отеля не может быть меньше 1")
        Long hotelId
) {
}
