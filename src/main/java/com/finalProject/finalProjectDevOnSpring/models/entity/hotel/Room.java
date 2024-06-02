package com.finalProject.finalProjectDevOnSpring.models.entity.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldNameConstants
@Entity
@Table(name = "ROOMS")
public class Room extends BaseEntity {
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String roomName;
    @Column(columnDefinition = "VARCHAR NOT NULL")
    private String roomDescription;
    @Column(columnDefinition = "INT NOT NULL")
    private Integer roomNumber;
    @Column(columnDefinition = "INT NOT NULL")
    private Integer maximumCapacity;
    private BigDecimal roomPrice;
    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
