package com.gr74.ScreenMaster.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Movie extends BaseEntity {

    @Id
    private int id;

    private String title;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "original_language")
    private String originalLanguage;

    @Column(length = 4000)
    private String overview;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "backdrop_path")
    private String backdropPath;

    private boolean adult;

    private double popularity;

    @Column(name = "vote_average")
    private double voteAverage;

    @Column(name = "vote_count")
    private int voteCount;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToMany
    @JoinTable(
        name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Transient
    private List<Integer> genreIds; // Used for JSON serialization/deserialization
}
