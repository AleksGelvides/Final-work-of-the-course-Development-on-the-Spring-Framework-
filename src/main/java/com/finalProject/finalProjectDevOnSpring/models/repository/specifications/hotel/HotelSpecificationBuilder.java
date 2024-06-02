package com.finalProject.finalProjectDevOnSpring.models.repository.specifications.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Hotel;
import com.finalProject.finalProjectDevOnSpring.models.repository.specifications.BaseSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.BaseSearchCriteria;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.HotelSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class HotelSpecificationBuilder implements BaseSpecificationBuilder<Hotel> {


    @Override
    public Specification<Hotel> findBy(BaseSearchCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            var hotelCriteria = (HotelSearchCriteria) criteria;
            if (Objects.isNull(criteria)) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(hotelCriteria.getId())) {
                predicates.add(criteriaBuilder.equal(root.get(BaseEntity.Fields.id), hotelCriteria.getId()));
            }

            if (StringUtils.isNoneEmpty(hotelCriteria.getHotelName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Hotel.Fields.hotelName)
                        ), toUpperSearch(hotelCriteria.getHotelName())));
            }

            if (StringUtils.isNoneEmpty(hotelCriteria.getHotelHeader())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Hotel.Fields.headerAd)
                        ), toUpperSearch(hotelCriteria.getHotelHeader())));
            }

            if (StringUtils.isNoneEmpty(hotelCriteria.getCity())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Hotel.Fields.cityName)
                        ), toUpperSearch(hotelCriteria.getCity())));
            }

            if (StringUtils.isNoneEmpty(hotelCriteria.getAddress())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Hotel.Fields.address)
                        ), toUpperSearch(hotelCriteria.getAddress())));
            }

            if (Objects.nonNull(hotelCriteria.getDistanceFromCityCenter())) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Hotel.Fields.distanceFromCityCenter), hotelCriteria.getDistanceFromCityCenter()));
            }

            if (Objects.nonNull(hotelCriteria.getRating())) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Hotel.Fields.rating), hotelCriteria.getRating()));
            }

            if (Objects.nonNull(hotelCriteria.getRating())) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Hotel.Fields.rating), hotelCriteria.getRating()));
            }

            if (Objects.nonNull(hotelCriteria.getRatingCount())) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Hotel.Fields.rateCount), hotelCriteria.getRatingCount()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
