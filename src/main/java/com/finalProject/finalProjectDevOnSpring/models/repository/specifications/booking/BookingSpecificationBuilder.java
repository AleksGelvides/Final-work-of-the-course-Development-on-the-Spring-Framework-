package com.finalProject.finalProjectDevOnSpring.models.repository.specifications.booking;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import com.finalProject.finalProjectDevOnSpring.models.entity.booking.Booking;
import com.finalProject.finalProjectDevOnSpring.models.repository.specifications.BaseSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.BaseSearchCriteria;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.BookingSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookingSpecificationBuilder implements BaseSpecificationBuilder<Booking> {

    public Specification<Booking> findBy(BaseSearchCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            var bookingCriteria = (BookingSearchCriteria) criteria;
            if (Objects.isNull(bookingCriteria)) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(bookingCriteria.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get(Booking.Fields.user).get(BaseEntity.Fields.id), bookingCriteria.getUserId()));
            }

            if (Objects.nonNull(bookingCriteria.getRoomId())) {
                predicates.add(criteriaBuilder.equal(root.get(Booking.Fields.room).get(BaseEntity.Fields.id), bookingCriteria.getRoomId()));
            }

            if (Objects.nonNull(bookingCriteria.getBookingFrom())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Booking.Fields.bookingFrom), bookingCriteria.getBookingFrom()));
            }

            if (Objects.nonNull(bookingCriteria.getBookingTo())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Booking.Fields.bookingTo), bookingCriteria.getBookingTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
