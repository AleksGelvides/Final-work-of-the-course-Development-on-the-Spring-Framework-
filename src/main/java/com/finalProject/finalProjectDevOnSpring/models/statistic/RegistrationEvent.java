package com.finalProject.finalProjectDevOnSpring.models.statistic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "registrations")
public class RegistrationEvent {
    private String id;
    private Long userId;
    private LocalDateTime createDateTime;
}
