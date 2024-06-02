package com.finalProject.finalProjectDevOnSpring.web.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelSearchCriteria extends BaseSearchCriteria {
    private Long id;
    private String hotelName;
    private String hotelHeader;
    private String city;
    private String address;
    private Integer distanceFromCityCenter;
    private Integer rating;
    private Integer ratingCount;
}
