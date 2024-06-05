package com.finalProject.finalProjectDevOnSpring.repository.stats;

import com.finalProject.finalProjectDevOnSpring.models.statistic.BookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingEventRepository extends MongoRepository<BookingEvent, String> {
}
