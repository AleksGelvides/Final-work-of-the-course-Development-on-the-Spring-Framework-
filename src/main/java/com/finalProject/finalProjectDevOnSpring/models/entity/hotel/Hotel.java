package com.finalProject.finalProjectDevOnSpring.models.entity.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldNameConstants
@Entity
@Table(name = "HOTELS")
public class Hotel extends BaseEntity {
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String hotelName;
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String headerAd;
    @Column(columnDefinition = "VARCHAR NOT NULL")
    private String cityName;
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String address;
    private Long distanceFromCityCenter;
    private Integer rating;
    private Integer rateCount;
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> room = new ArrayList<>();
}
