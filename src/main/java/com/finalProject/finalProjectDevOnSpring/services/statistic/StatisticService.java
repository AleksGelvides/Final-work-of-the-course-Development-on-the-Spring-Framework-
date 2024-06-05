package com.finalProject.finalProjectDevOnSpring.services.statistic;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.models.kafka.KafkaMessage;
import com.finalProject.finalProjectDevOnSpring.repository.stats.BookingEventRepository;
import com.finalProject.finalProjectDevOnSpring.repository.stats.RegistrationEventRepository;
import com.finalProject.finalProjectDevOnSpring.models.statistic.BookingEvent;
import com.finalProject.finalProjectDevOnSpring.models.statistic.RegistrationEvent;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private static long messageIg = 0;
    private final RegistrationEventRepository registrationEventRepository;
    private final BookingEventRepository bookingEventRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    @Value("${app.kafka.bookingEventMessageTopic}")
    private String bookingEventMessageTopic;
    @Value("${app.kafka.userRegistrationEventMessageTopic}")
    private String userRegistrationEventMessageTopic;

    public void createRegistrationEvent(Long userId) throws ApplicationException {
        RegistrationEvent registrationEvent = new RegistrationEvent()
                .setId(UUID.randomUUID().toString())
                .setUserId(userId)
                .setCreateDateTime(LocalDateTime.now());

        registrationEventRepository.save(registrationEvent);
        try {
            kafkaTemplate.send(userRegistrationEventMessageTopic,
                    new KafkaMessage(++messageIg, objectMapper.writeValueAsString(registrationEvent)));
        } catch (JsonProcessingException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void createBookingEvent(BookingRequest request) throws ApplicationException {
        BookingEvent bookingEvent = new BookingEvent()
                .setId(UUID.randomUUID().toString())
                .setUserId(request.userId())
                .setRoomId(request.roomId())
                .setBookingStart(request.bookingFrom())
                .setBookingEnd(request.bookingTo());

        bookingEventRepository.save(bookingEvent);
        try {
            kafkaTemplate.send(bookingEventMessageTopic,
                    new KafkaMessage(++messageIg, objectMapper.writeValueAsString(bookingEvent.toString())));
        } catch (JsonProcessingException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void getStatisticRegistrationEvent(HttpServletResponse response) throws Exception {
        Workbook wkb = new Workbook();
        Worksheet worksheet = wkb.getWorksheets().get(0);
        worksheet.getCells().get("A1").putValue("ID записи");
        worksheet.getCells().get("B1").putValue("ID зарегистрированного пользователя");
        worksheet.getCells().get("C1").putValue("Время регистрации");

        AtomicLong cellNumber = new AtomicLong(2);
        registrationEventRepository.findAll()
                .forEach(record -> {
            worksheet.getCells().get("A" + cellNumber).putValue(record.getId());
            worksheet.getCells().get("B" + cellNumber).putValue(record.getUserId());
            worksheet.getCells().get("C" + cellNumber).putValue(record.getCreateDateTime().toString());
            cellNumber.getAndIncrement();
        });

        try (OutputStream out = response.getOutputStream()) {
            wkb.save(out, com.aspose.cells.SaveFormat.CSV);
        }
    }

    public void getStatisticBookingEvent(HttpServletResponse response) throws Exception {
        Workbook wkb = new Workbook();
        Worksheet worksheet = wkb.getWorksheets().get(0);
        worksheet.getCells().get("A1").putValue("ID записи");
        worksheet.getCells().get("B1").putValue("ID зарегистрированного пользователя");
        worksheet.getCells().get("C1").putValue("ID забронированной комнаты");
        worksheet.getCells().get("D1").putValue("Время заселения");
        worksheet.getCells().get("E1").putValue("Время выселения");

        AtomicLong cellNumber = new AtomicLong(2);
        bookingEventRepository.findAll()
                .forEach(record -> {
                    worksheet.getCells().get("A" + cellNumber).putValue(record.getId());
                    worksheet.getCells().get("B" + cellNumber).putValue(record.getUserId());
                    worksheet.getCells().get("C" + cellNumber).putValue(record.getRoomId());
                    worksheet.getCells().get("D" + cellNumber).putValue(record.getBookingStart().toString());
                    worksheet.getCells().get("E" + cellNumber).putValue(record.getBookingEnd().toString());
                    cellNumber.getAndIncrement();
                });

        try (OutputStream out = response.getOutputStream()) {
            wkb.save(out, com.aspose.cells.SaveFormat.CSV);
        }
    }
}
