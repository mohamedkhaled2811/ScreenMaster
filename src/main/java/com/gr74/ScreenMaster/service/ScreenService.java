package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.dto.ScreenRequestDto;
import com.gr74.ScreenMaster.model.Screen;
import com.gr74.ScreenMaster.model.Theater;
import com.gr74.ScreenMaster.repository.ScreenRepository;
import com.gr74.ScreenMaster.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    public List<?> getScreens(Integer theaterId){
        return screenRepository.findByTheaterId(theaterId);
    }

    public void addScreen(ScreenRequestDto screenRequestDto){
        Screen screen = convertToEntity(screenRequestDto);
        screenRepository.save(screen);
    }



    public void deleteScreen(Integer screenId){
        screenRepository.deleteById(screenId);
    }

    private Screen convertToEntity(ScreenRequestDto dto) {

        Theater theater = theaterRepository.getReferenceById(dto.getTheaterId());

        // Create and return the Screen entity
        return Screen.builder()
                .name(dto.getName())
                .totalSeats(dto.getTotalSeats())
                .screenType(dto.getScreenType())
                .is_active(dto.is_active())
                .theater(theater)
                .build();
    }

}
