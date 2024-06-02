package com.finalProject.finalProjectDevOnSpring.services.booking;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import com.finalProject.finalProjectDevOnSpring.models.repository.specifications.booking.BookingSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.models.repository.BookingRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.booking.BookingMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.services.statistic.StatisticService;
import com.finalProject.finalProjectDevOnSpring.web.dto.booking.BookingBaseDto;
import com.finalProject.finalProjectDevOnSpring.web.dto.booking.BookingRequest;
import org.springframework.stereotype.Service;

@Service
public class BookingService extends CommonService<BookingBaseDto, BookingRequest, Booking, Long> {
    private final static String BOOKING_DATE_ERROR = "The booking time is overlapping with an existing booking";
    private final StatisticService statisticService;

    public BookingService(BookingRepository bookingRepository,
                          BookingMapper bookingMapper,
                          BookingSpecificationBuilder specificationBuilder,
                          StatisticService statisticService) {
        super(bookingRepository, bookingMapper, specificationBuilder);
        this.statisticService = statisticService;
    }


    public BookingBaseDto bookRoom(BookingRequest request) throws ApplicationException, ApplicationNotFoundException {
        dateIsNotOccupiedCheck(request);
        statisticService.createBookingEvent(request);
        return saveOrUpdate(null, request);
    }

    private void dateIsNotOccupiedCheck(BookingRequest request) throws ApplicationException {
        if (((BookingRepository) repository).dateIsOccupied(request.bookingFrom(), request.bookingTo()))
            throw new ApplicationException(BOOKING_DATE_ERROR);
    }
}
