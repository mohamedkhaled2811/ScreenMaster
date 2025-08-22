package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.dto.response.SeatTypeRequestDto;
import com.gr74.ScreenMaster.dto.response.SeatTypeResponseDto;
import com.gr74.ScreenMaster.model.SeatType;
import com.gr74.ScreenMaster.repository.SeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;



    public List<SeatTypeResponseDto> getSeatTypes() {
        return seatTypeRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private SeatTypeResponseDto convertToResponseDto(SeatType seatType) {
        return SeatTypeResponseDto.builder().id(seatType.getId())
                .name(seatType.getName())
                .priceMultiplier(seatType.getPriceMultiplier())
                .description(seatType.getDescription()).is_active(seatType.is_active()).build();
    }


    public void addSeatType(SeatTypeRequestDto seatTypeRequest){

        var seatType = SeatType.builder()
                .name(seatTypeRequest.getName())
                .priceMultiplier(seatTypeRequest.getPriceMultiplier())
                .description(seatTypeRequest.getDescription())
                .is_active(seatTypeRequest.is_active())
                .build();

         seatTypeRepository.save(seatType);

    }

    public void deleteSeatType(Integer Id){
        seatTypeRepository.deleteById(Id);
    }



}
