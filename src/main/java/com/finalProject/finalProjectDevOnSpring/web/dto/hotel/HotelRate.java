package com.finalProject.finalProjectDevOnSpring.web.dto.hotel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HotelRate(
        @Min(value = 1, message = "hotelId не может быть меньше 1")
        @NotNull(message = "hotelId не может быть пустым значением")
        Long hotelId,
        @Min(value = 1, message = "Оценка не может быть меньше 1")
        @Max(value = 5, message = "Оценка не может быть больше 5")
        @NotNull(message = "rating не может быть пустым значением")
        Integer rating
) {
}
