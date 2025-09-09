package com.gr74.ScreenMaster.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TMDBMovieResponse {
    private int id;
    private String title;
    private String overview;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    private Double popularity;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("original_language")
    private String originalLanguage;

    private Boolean adult;
    private Boolean video;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
}
