package com.finalProject.finalProjectDevOnSpring.mapper.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Hotel;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.dto.hotel.HotelChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.hotel.HotelDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class, uses = RoomMapper.class)
public interface HotelMapper extends BaseMapper<Hotel, HotelDto, HotelChangeRequest> {
}
