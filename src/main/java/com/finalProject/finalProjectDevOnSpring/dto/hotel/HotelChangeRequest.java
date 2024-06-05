package com.finalProject.finalProjectDevOnSpring.dto.hotel;

public record HotelChangeRequest(
        String hotelName,
        String headerAd,
        String cityName,
        String address,
        Long distanceFromCityCenter
) {
}
