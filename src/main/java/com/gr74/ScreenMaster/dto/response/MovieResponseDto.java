package com.gr74.ScreenMaster.dto.response;


import com.gr74.ScreenMaster.model.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class MovieResponseDto {

    private int id;
    private String title;
    private String originalLanguage;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private boolean adult;
    private double popularity;
    private LocalDate releaseDate;
    private List<Genre> genres;


}
