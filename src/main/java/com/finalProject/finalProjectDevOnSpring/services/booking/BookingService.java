package com.finalProject.finalProjectDevOnSpring.services.booking;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import com.finalProject.finalProjectDevOnSpring.repository.specifications.booking.BookingSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.repository.BookingRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.booking.BookingMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.services.hotel.RoomService;
import com.finalProject.finalProjectDevOnSpring.services.statistic.StatisticService;
import com.finalProject.finalProjectDevOnSpring.services.user.UserService;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingBaseDto;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public class BookingService extends CommonService<BookingBaseDto, BookingRequest, Booking, Long> {
    private final static String BOOKING_DATE_ERROR = "The booking time is overlapping with an existing booking";
    private final StatisticService statisticService;
    private final UserService userService;
    private final RoomService roomService;

    public BookingService(BookingRepository bookingRepository,
                          BookingMapper bookingMapper,
                          BookingSpecificationBuilder specificationBuilder,
                          StatisticService statisticService,
                          UserService userService,
                          RoomService roomService) {
        super(bookingRepository, bookingMapper, specificationBuilder);
        this.statisticService = statisticService;
        this.userService = userService;
        this.roomService = roomService;
    }


    public BookingBaseDto bookRoom(BookingRequest request) throws ApplicationException, ApplicationNotFoundException {
        dateIsNotOccupiedCheck(request);
        statisticService.createBookingEvent(request);
        return saveOrUpdate(null, request);
    }

    @Override
    public BookingBaseDto saveOrUpdate(Long id, BookingRequest request) throws ApplicationNotFoundException, ApplicationException {
        if (Objects.isNull(id)) {
            return mapper.toDto(repository.save(mapRequestToBooking(request)));
        } else {
            Booking existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Booking with ID: {0} not found", id))
                    );
            Booking updatedEntity = mapper.merge(existingEntity, mapRequestToBooking(request));
            return mapper.toDto(repository.save(updatedEntity));
        }
    }


    private Booking mapRequestToBooking(BookingRequest request) throws ApplicationNotFoundException {
        return mapper.changeToEntity(request)
                .setUser(userService.findUserById(request.userId()))
                .setRoom(roomService.findRoomById(request.roomId()));
    }

    private void dateIsNotOccupiedCheck(BookingRequest request) throws ApplicationException {
        if (((BookingRepository) repository).dateIsOccupied(request.bookingFrom(), request.bookingTo()))
            throw new ApplicationException(BOOKING_DATE_ERROR);
    }
}
