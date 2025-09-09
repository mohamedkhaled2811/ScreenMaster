package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.dto.response.GenresResponseDto;
import com.gr74.ScreenMaster.model.Genre;
import com.gr74.ScreenMaster.repository.GenresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenresService {

    private final GenresRepository genresRepository;


    public List<GenresResponseDto> getGenres(){
        return genresRepository.findAll().stream().map(this::convertToGenresResponseDto).collect(Collectors.toList());
    }


    private GenresResponseDto convertToGenresResponseDto(Genre genre){
        return GenresResponseDto.builder().name(genre.getName()).build();
    }

}
