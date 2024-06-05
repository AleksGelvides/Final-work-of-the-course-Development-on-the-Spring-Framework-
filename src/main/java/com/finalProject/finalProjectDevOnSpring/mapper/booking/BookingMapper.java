package com.finalProject.finalProjectDevOnSpring.mapper.booking;

import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.mapper.user.UserMapper;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingBaseDto;
import com.finalProject.finalProjectDevOnSpring.dto.booking.BookingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(config = BaseMapper.class, uses = {
        UserMapper.class,
})
public interface BookingMapper extends BaseMapper<Booking, BookingBaseDto, BookingRequest> {

    @Override
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "room", ignore = true)
    Booking changeToEntity(BookingRequest request);
}
