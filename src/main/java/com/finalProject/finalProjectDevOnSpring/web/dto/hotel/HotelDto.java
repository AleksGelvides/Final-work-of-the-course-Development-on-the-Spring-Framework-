package com.finalProject.finalProjectDevOnSpring.web.dto.hotel;

public record HotelDto(
        Long id,
        String hotelName,
        String headerAd,
        String cityName,
        String address,
        Long distanceFromCityCenter,
        Integer rating,
        Integer rateCount
) {
}
