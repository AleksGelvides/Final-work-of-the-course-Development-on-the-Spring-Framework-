package com.finalProject.finalProjectDevOnSpring.models.statistic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "bookings")
public class BookingEvent {
    private String id;
    private Long userId;
    private Long roomId;
    private LocalDateTime bookingStart;
    private LocalDateTime bookingEnd;
}
