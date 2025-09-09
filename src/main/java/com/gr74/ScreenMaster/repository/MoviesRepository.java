package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.model.Movie;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {



    List<Movie> findAll(Specification<Movie> specification);


    // Basic finder methods
    List<Movie> findByTitle(String title);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByOriginalTitle(String originalTitle);
    List<Movie> findByOriginalTitleContainingIgnoreCase(String originalTitle);

    // Date-based queries
    List<Movie> findByReleaseDateAfter(LocalDate date);
    List<Movie> findByReleaseDateBefore(LocalDate date);
    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    // Rating and popularity queries
    List<Movie> findByVoteAverageGreaterThanEqual(double rating);
    List<Movie> findTop10ByOrderByPopularityDesc();
    List<Movie> findTop10ByOrderByVoteAverageDesc();

//    // Genre-related queries
//    @Query("SELECT m FROM Movies m JOIN m.genres g WHERE g.id = :genreId")
//    List<Movie> findByGenreId(@Param("genreId") Integer genreId);
//
//    @Query("SELECT m FROM Movies m JOIN m.genres g WHERE g.name = :genreName")
//    List<Movie> findByGenreName(@Param("genreName") String genreName);
}
