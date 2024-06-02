package com.finalProject.finalProjectDevOnSpring.web.dto.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RoomSearchCriteria extends BaseSearchCriteria {
    private Long id;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer maximumCapacity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookingFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookingTo;
    private Long hotelId;
}
