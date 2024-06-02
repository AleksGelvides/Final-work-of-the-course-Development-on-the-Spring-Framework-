package com.finalProject.finalProjectDevOnSpring.models.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaMessage {
    private Long id;
    private String message;
}
