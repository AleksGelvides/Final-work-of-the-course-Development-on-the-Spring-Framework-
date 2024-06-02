package com.finalProject.finalProjectDevOnSpring.services.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.models.repository.RoomRepository;
import com.finalProject.finalProjectDevOnSpring.models.repository.specifications.hotel.RoomSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.mapper.hotel.RoomMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomDto;
import org.springframework.stereotype.Service;


@Service
public class RoomService extends CommonService<RoomDto, RoomChangeRequest, Room, Long> {

    public RoomService(RoomRepository roomRepository,
                       RoomMapper roomMapper,
                       RoomSpecificationBuilder specificationBuilder) {
        super(roomRepository, roomMapper, specificationBuilder);
    }
}
