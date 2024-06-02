package com.finalProject.finalProjectDevOnSpring.models.repository.stats;

import com.finalProject.finalProjectDevOnSpring.models.statistic.RegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationEventRepository extends MongoRepository<RegistrationEvent, String> {
}
