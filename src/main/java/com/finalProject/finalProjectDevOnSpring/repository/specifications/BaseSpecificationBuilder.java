package com.finalProject.finalProjectDevOnSpring.repository.specifications;

import com.finalProject.finalProjectDevOnSpring.dto.search.BaseSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public interface BaseSpecificationBuilder<T> {

    public Specification<T> findBy(BaseSearchCriteria criteria);

    default String toUpperSearch(String str) {
        return MessageFormat.format("%{0}%", str.toUpperCase());
    }
}
