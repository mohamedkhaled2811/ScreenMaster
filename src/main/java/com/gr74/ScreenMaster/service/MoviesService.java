package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.dto.request.MovieRequestDto;
import com.gr74.ScreenMaster.dto.request.SortAndFilterRequestDto;
import com.gr74.ScreenMaster.dto.response.MovieResponseDto;
import com.gr74.ScreenMaster.dto.request.SortDto;
import com.gr74.ScreenMaster.model.Movie;
import com.gr74.ScreenMaster.repository.GenresRepository;
import com.gr74.ScreenMaster.repository.MoviesRepository;
import com.gr74.ScreenMaster.specification.MovieSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoviesService {


    private final MoviesRepository moviesRepository;
    private final GenresRepository genresRepository;



    public List<MovieResponseDto> getMovies(MovieRequestDto movieRequestDto){


        // Parse and create sort orders
        List<SortDto> sortDtos = movieRequestDto.getSortDto();
        List<Sort.Order> order = new ArrayList<>();

        if (sortDtos != null) {
            for(SortDto sortDto: sortDtos) {
                Sort.Direction direction = Objects.equals(sortDto.getDirection(), "desc")
                        ? Sort.Direction.DESC : Sort.Direction.ASC;
                order.add(new Sort.Order(direction,sortDto.getField()));
            }
        }


        PageRequest pageRequest = PageRequest.of(
                movieRequestDto.getPage(),
                movieRequestDto.getSize(),
                Sort.by(order)
        );


        Specification<Movie> specification = MovieSpecification.getSpecification(movieRequestDto);

//        List<MovieResponseDto> movies = moviesRepository.findAllMoviesWithGenres(specification,pageRequest)
        List<MovieResponseDto> movies = moviesRepository.findAll(specification,pageRequest)
                .stream()
                .map(this::convertToMovieResponseDto)
                .collect(Collectors.toList());


        return movies;
    }


    private MovieResponseDto convertToMovieResponseDto(Movie movie){
        return MovieResponseDto
                .builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .overview(movie.getOverview())
                .releaseDate(movie.getReleaseDate())
                .originalLanguage(movie.getOriginalLanguage())
                .posterPath(movie.getPosterPath())
                .backdropPath(movie.getBackdropPath())
                .adult(movie.isAdult())
                .popularity(movie.getPopularity())
                .genres(movie.getGenres())
                .build();
    }



}
