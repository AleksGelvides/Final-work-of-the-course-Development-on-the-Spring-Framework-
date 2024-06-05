package com.finalProject.finalProjectDevOnSpring.services.hotel;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.repository.RoomRepository;
import com.finalProject.finalProjectDevOnSpring.repository.specifications.hotel.RoomSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.mapper.hotel.RoomMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.dto.room.RoomChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.room.RoomDto;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;


@Service
public class RoomService extends CommonService<RoomDto, RoomChangeRequest, Room, Long> {
    private final HotelService hotelService;

    public RoomService(RoomRepository roomRepository,
                       RoomMapper roomMapper,
                       RoomSpecificationBuilder specificationBuilder,
                       HotelService hotelService) {
        super(roomRepository, roomMapper, specificationBuilder);
        this.hotelService = hotelService;
    }

    public Room findRoomById(Long roomId) throws ApplicationNotFoundException {
        return repository.findById(roomId).orElseThrow(() -> new ApplicationNotFoundException(
                MessageFormat.format("Room with id: {0} not found", roomId))
        );
    }

    @Override
    public RoomDto saveOrUpdate(Long id, RoomChangeRequest request) throws ApplicationNotFoundException, ApplicationException {
        if (Objects.isNull(id)) {
            return mapper.toDto(repository.save(mapRequestToBooking(request)));
        } else {
            Room existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Room with ID: {0} not found", id))
                    );
            Room updatedEntity = mapper.merge(existingEntity, mapRequestToBooking(request));
            return mapper.toDto(repository.save(updatedEntity));
        }
    }

    private Room mapRequestToBooking(RoomChangeRequest request) throws ApplicationNotFoundException {
        return mapper.changeToEntity(request)
                .setHotel(hotelService.findHotel(request.hotelId()));
    }
}
