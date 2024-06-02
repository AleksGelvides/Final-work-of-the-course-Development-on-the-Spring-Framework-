package com.finalProject.finalProjectDevOnSpring.mapper.hotel;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Hotel;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.models.repository.HotelRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.mapper.booking.BookingMapper;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

@Mapper(config = BaseMapper.class, uses = {
        HotelMapper.class,
        BookingMapper.class
})
public abstract class RoomMapper implements BaseMapper<Room, RoomDto, RoomChangeRequest> {
    @Autowired
    private HotelRepository hotelRepository;

    @Mapping(target = "hotel", source = "hotelId", qualifiedByName = "getHotel")
    @Mapping(target = "roomNumber", ignore = true)
    public abstract Room updateFromChangeRequest(RoomChangeRequest roomChangeRequest);

    @Override
    @Mapping(target = "hotel", source = "hotelId", qualifiedByName = "getHotel")
    public abstract Room changeToEntity(RoomChangeRequest roomChangeRequest);

    @Named("getHotel")
    public Hotel getHotel(Long hotelId) throws ApplicationNotFoundException {
        return hotelRepository.findById(hotelId).orElseThrow(
                () -> new ApplicationNotFoundException(MessageFormat.format("Hotel with ID {0} not found", hotelId))
        );
    }
}
