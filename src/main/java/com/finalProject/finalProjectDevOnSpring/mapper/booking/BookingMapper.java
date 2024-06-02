package com.finalProject.finalProjectDevOnSpring.mapper.booking;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import com.finalProject.finalProjectDevOnSpring.models.repository.RoomRepository;
import com.finalProject.finalProjectDevOnSpring.models.repository.UserRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.mapper.user.UserMapper;
import com.finalProject.finalProjectDevOnSpring.web.dto.booking.BookingBaseDto;
import com.finalProject.finalProjectDevOnSpring.web.dto.booking.BookingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

@Mapper(config = BaseMapper.class, uses = {
        UserMapper.class,
})
public abstract class BookingMapper implements BaseMapper<Booking, BookingBaseDto, BookingRequest> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    @Mapping(target = "user", source = "userId", qualifiedByName = "findUser")
    @Mapping(target = "room", source = "roomId", qualifiedByName = "findRoom")
    public abstract Booking changeToEntity(BookingRequest request);

    @Named("findUser")
    public User findUser(Long userId) throws ApplicationNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        MessageFormat.format("User with ID: {0} not found", userId))
                );
    }

    @Named("findRoom")
    public Room findRoom(Long roomId) throws ApplicationNotFoundException {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        MessageFormat.format("Room with ID: {0} not found", roomId))
                );
    }
}
