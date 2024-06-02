package com.finalProject.finalProjectDevOnSpring.models.entity.booking;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@FieldNameConstants
@Table(name = "BOOKING")
public class Booking extends BaseEntity {
    private LocalDateTime bookingFrom;
    private LocalDateTime bookingTo;
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;
    private String comment;
}
