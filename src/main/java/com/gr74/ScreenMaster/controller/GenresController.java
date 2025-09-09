package com.gr74.ScreenMaster.controller;


import com.gr74.ScreenMaster.dto.response.GenresResponseDto;
import com.gr74.ScreenMaster.service.GenresService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
public class GenresController {


    private final GenresService genresService;


    @GetMapping("/")
    public ResponseEntity<List<GenresResponseDto>> getGenres(){
        return ResponseEntity.ok(genresService.getGenres());
    }


}
