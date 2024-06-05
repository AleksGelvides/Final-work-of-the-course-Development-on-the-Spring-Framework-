package com.finalProject.finalProjectDevOnSpring.mapper.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.mapper.booking.BookingMapper;
import com.finalProject.finalProjectDevOnSpring.dto.room.RoomChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.room.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(config = BaseMapper.class, uses = {
        HotelMapper.class,
        BookingMapper.class
})
public interface RoomMapper extends BaseMapper<Room, RoomDto, RoomChangeRequest> {

    @Override
    @Mapping(target = "hotel", ignore = true)
    Room changeToEntity(RoomChangeRequest roomChangeRequest);
}
