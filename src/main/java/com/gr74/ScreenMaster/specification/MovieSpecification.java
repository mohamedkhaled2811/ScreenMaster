package com.gr74.ScreenMaster.specification;


import com.gr74.ScreenMaster.dto.request.MovieRequestDto;
import com.gr74.ScreenMaster.model.Genre;
import com.gr74.ScreenMaster.model.Movie;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MovieSpecification {
    public static Specification<Movie> getSpecification(MovieRequestDto filterDto) {
        return (root, query, criteriaBuilder) -> {//TODO : how the jpa got movies without duplication



            // ✨ Add a fetch join to load the genres collection eagerly.
            // Using a LEFT join ensures movies without any genres are still returned.
            // This should be done before adding predicates to avoid issues with count queries.
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("genres", JoinType.LEFT);
            }


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


            if (filterDto.getGenres() != null && !filterDto.getGenres().isEmpty()) {
                Join<Movie, Genre> genreJoin = root.join("genres", JoinType.INNER);
                predicates.add(genreJoin.get("name").in(filterDto.getGenres()));
                query.distinct(true); // avoid duplicates
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}

