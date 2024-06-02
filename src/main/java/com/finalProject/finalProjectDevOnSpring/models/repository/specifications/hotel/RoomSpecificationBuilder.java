package com.finalProject.finalProjectDevOnSpring.models.repository.specifications.hotel;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.models.repository.specifications.BaseSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.BaseSearchCriteria;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.RoomSearchCriteria;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RoomSpecificationBuilder implements BaseSpecificationBuilder<Room> {
    @Override
    public Specification<Room> findBy(BaseSearchCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            var roomCriteria = (RoomSearchCriteria) criteria;
            if (Objects.isNull(roomCriteria)) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(roomCriteria.getId())) {
                predicates.add(criteriaBuilder.equal(root.get(BaseEntity.Fields.id), roomCriteria.getId()));
            }

            if (StringUtils.isNoneEmpty(roomCriteria.getName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Room.Fields.roomName)),
                        toUpperSearch(roomCriteria.getName())));
            }

            if (Objects.nonNull(roomCriteria.getMinPrice())) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(Room.Fields.roomPrice), roomCriteria.getMinPrice())
                );
            }

            if (Objects.nonNull(roomCriteria.getMaxPrice())) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get(Room.Fields.roomPrice), roomCriteria.getMaxPrice())
                );
            }

            if (Objects.nonNull(roomCriteria.getMaximumCapacity())) {
                predicates.add(
                        criteriaBuilder.equal(root.get(Room.Fields.maximumCapacity), roomCriteria.getMaximumCapacity())
                );
            }

            if (Objects.nonNull(roomCriteria.getHotelId())) {
                predicates.add(
                        criteriaBuilder.equal(root.get(Room.Fields.hotel).get(BaseEntity.Fields.id), roomCriteria.getHotelId())
                );
            }

            if (Objects.nonNull(roomCriteria.getBookingFrom()) && Objects.nonNull(roomCriteria.getBookingTo())) {
                Join<Room, Booking> bookingJoin = root.join(Room.Fields.bookings, JoinType.LEFT);
                predicates.add(criteriaBuilder.or(
                                criteriaBuilder.isNull(bookingJoin.get(Booking.Fields.bookingFrom)),
                                criteriaBuilder.isNull(bookingJoin.get(Booking.Fields.bookingTo)),
                                criteriaBuilder.and(
                                        criteriaBuilder.lessThanOrEqualTo(bookingJoin.get(Booking.Fields.bookingTo), roomCriteria.getBookingFrom()),
                                        criteriaBuilder.greaterThanOrEqualTo(bookingJoin.get(Booking.Fields.bookingFrom), roomCriteria.getBookingTo())
                                )
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
