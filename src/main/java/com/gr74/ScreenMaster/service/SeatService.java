package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.dto.request.SeatRequestDto;
import com.gr74.ScreenMaster.model.Screen;
import com.gr74.ScreenMaster.model.Seat;
import com.gr74.ScreenMaster.model.SeatType;
import com.gr74.ScreenMaster.repository.ScreenRepository;
import com.gr74.ScreenMaster.repository.SeatRepository;
import com.gr74.ScreenMaster.repository.SeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final ScreenRepository screenRepository;


    public List<?> getScreenSeats(Integer screenId){
        return seatRepository.findByScreenId(screenId);
    }

    public void addScreenSeat(SeatRequestDto seatRequestDto){
        Seat seat = convertToEntity(seatRequestDto);
        seatRepository.save(seat);
    }

    public void deleteSeat(Integer seatId){
        seatRepository.deleteById(seatId);
    }

    private Seat convertToEntity(SeatRequestDto dto) {
        // Create references to related entities without querying the database
        SeatType seatType = seatTypeRepository.getReferenceById(dto.getSeatTypeId());
        Screen screen = screenRepository.getReferenceById(dto.getScreenId());

        // Create and return the Seat entity
        return Seat.builder()
                .seatNumber(dto.getSeatNumber())
                .rowNumber(dto.getRowNumber())
                .is_available(dto.is_available())
                .is_accessible(dto.is_accessible())
                .seatType(seatType)
                .screen(screen)
                .build();
    }
}
