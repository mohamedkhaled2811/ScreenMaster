package com.gr74.ScreenMaster.controller;


import com.gr74.ScreenMaster.dto.request.MovieRequestDto;
import com.gr74.ScreenMaster.dto.request.SortAndFilterRequestDto;
import com.gr74.ScreenMaster.dto.response.MovieResponseDto;
import com.gr74.ScreenMaster.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;


    @PostMapping("/filter")
    public ResponseEntity<List<MovieResponseDto>> getMovies(
            @RequestBody(required = false) SortAndFilterRequestDto sortAndFilterRequestDto,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "overview", defaultValue = "") String overview,
            @RequestParam(name = "adult", defaultValue = "false") String adult
    )
    {
        MovieRequestDto movieRequestDto = MovieRequestDto.builder()
                .title(title)
                .overview(overview)
                .adult(Boolean.parseBoolean(adult))
                .page(page)
                .size(size)
                .sortDto(sortAndFilterRequestDto.getSort())
                .genres(sortAndFilterRequestDto.getGenres())
                .build();

        List<MovieResponseDto> movies = moviesService.getMovies(movieRequestDto);

        return ResponseEntity.ok(movies) ;
    }


}
