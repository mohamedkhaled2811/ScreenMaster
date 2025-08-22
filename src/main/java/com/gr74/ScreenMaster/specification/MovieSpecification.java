package com.gr74.ScreenMaster.specification;


import com.gr74.ScreenMaster.dto.request.MovieRequestDto;
import com.gr74.ScreenMaster.model.Movie;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MovieSpecification {
    public static Specification<Movie> getSpecification(MovieRequestDto filterDto) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filterDto.getTitle() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        filterDto.getTitle().toLowerCase()+"%")
                );
            }

            if (filterDto.getOverview() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("overview")),
                        "%"+filterDto.getOverview().toLowerCase()+"%")
                );
            }
            if (filterDto.getAdult() != null) {
                predicates.add(criteriaBuilder.equal(root.get("adult"),filterDto.getAdult()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}

