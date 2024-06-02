package com.finalProject.finalProjectDevOnSpring.services.hotel;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Hotel;
import com.finalProject.finalProjectDevOnSpring.models.repository.HotelRepository;
import com.finalProject.finalProjectDevOnSpring.models.repository.specifications.hotel.HotelSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.mapper.hotel.HotelMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelDto;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelRate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class HotelService extends CommonService<HotelDto, HotelChangeRequest, Hotel, Long> {
    public HotelService(HotelRepository hotelRepository,
                        HotelMapper hotelMapper,
                        HotelSpecificationBuilder specificationBuilder) {
        super(hotelRepository, hotelMapper, specificationBuilder);
    }

    public HotelDto rate(HotelRate rate) throws ApplicationNotFoundException {
        var hotel = findHotel(rate.hotelId());
        //totalRating = rating × numberOfRating
        var totalRating = hotel.getRating() * hotel.getRateCount();
        //totalRating = totalRating − rating + newMark
        totalRating = totalRating - hotel.getRating() + rate.rating();
        //rating = totalRating / numberOfRating
        hotel.setRating(totalRating/hotel.getRateCount());
        //numberOfRating = numberOfRating + 1
        hotel.setRateCount(hotel.getRateCount() + 1);
        return (HotelDto) mapper.toDto(repository.save(hotel));
    }

    private Hotel findHotel(Long hotelId) throws ApplicationNotFoundException {
        try {
            return repository.findById(hotelId)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Hotel with username: {0} not found", hotelId))
                    );
        } catch (Throwable e) {
            throw new ApplicationNotFoundException(e.getMessage());
        }
    }
}
