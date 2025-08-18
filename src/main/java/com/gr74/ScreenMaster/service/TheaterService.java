package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.dto.TheaterRequestDto;
import com.gr74.ScreenMaster.model.Theater;
import com.gr74.ScreenMaster.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public List<?> getTheaters(){
        return theaterRepository.findAll();
    }

    public void addTheater(TheaterRequestDto theaterRequestDto){
        Theater theater = convertToEntity(theaterRequestDto);
        theaterRepository.save(theater);
    }

    public void addTheater(Theater theater){
        theaterRepository.save(theater);
    }

    public void deleteTheater(Integer theaterId){
        theaterRepository.deleteById(theaterId);
    }

    private Theater convertToEntity(TheaterRequestDto dto) {
        return Theater.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .is_active(dto.is_active())
                .build();
    }
}
