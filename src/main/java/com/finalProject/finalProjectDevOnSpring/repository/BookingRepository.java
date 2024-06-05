package com.finalProject.finalProjectDevOnSpring.repository;

import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BookingRepository extends BaseRepository<Booking, Long> {

    @Query(value = """
            SELECT count(*) > 0
            FROM Booking
            WHERE (bookingFrom < :newBookingTo AND bookingTo > :newBookingFrom)
               OR (bookingFrom < :newBookingFrom AND bookingTo > :newBookingFrom)
               OR (bookingFrom < :newBookingTo AND bookingTo > :newBookingTo)
            """)
    Boolean dateIsOccupied(LocalDateTime newBookingFrom, LocalDateTime newBookingTo);
}
