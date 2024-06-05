package com.finalProject.finalProjectDevOnSpring.repository;

import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepository extends BaseRepository<Room, Long> {

    Page<Room> findAllByHotelId(Long hotelId, Pageable pageable);
}
