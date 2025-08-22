package com.gr74.ScreenMaster.controller;


import com.gr74.ScreenMaster.dto.request.MovieRequestDto;
import com.gr74.ScreenMaster.dto.request.SortRequestDto;
import com.gr74.ScreenMaster.dto.response.MovieResponseDto;
import com.gr74.ScreenMaster.dto.request.SortDto;
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

    @GetMapping("/filter")
    public ResponseEntity<List<MovieResponseDto>> getMovies(
            @RequestBody(required = false) SortRequestDto sort,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "title", defaultValue = "m") String title,
            @RequestParam(name = "overview", defaultValue = "m") String overview,
            @RequestParam(name = "adult", defaultValue = "false") String adult
    )
    {
        System.out.println(sort);
        MovieRequestDto movieRequestDto = MovieRequestDto.builder()
                .title(title)
                .overview(overview)
                .adult(Boolean.parseBoolean(adult))
                .page(page)
                .size(size)
                .sortDto(sort)
                .build();

        List<MovieResponseDto> movies = moviesService.getMovies(movieRequestDto);

        return ResponseEntity.ok(movies) ;
    }


}
