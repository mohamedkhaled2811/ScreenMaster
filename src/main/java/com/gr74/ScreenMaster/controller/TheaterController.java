package com.gr74.ScreenMaster.controller;

import com.gr74.ScreenMaster.dto.ScreenRequestDto;
import com.gr74.ScreenMaster.dto.SeatRequestDto;
import com.gr74.ScreenMaster.dto.SeatTypeRequestDto;
import com.gr74.ScreenMaster.dto.SeatTypeResponseDto;
import com.gr74.ScreenMaster.dto.TheaterRequestDto;
import com.gr74.ScreenMaster.model.Screen;
import com.gr74.ScreenMaster.model.Seat;
import com.gr74.ScreenMaster.model.SeatType;
import com.gr74.ScreenMaster.model.Theater;
import com.gr74.ScreenMaster.service.ScreenService;
import com.gr74.ScreenMaster.service.SeatService;
import com.gr74.ScreenMaster.service.SeatTypeService;
import com.gr74.ScreenMaster.service.TheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("theater")
@RequiredArgsConstructor
public class TheaterController {

    private final SeatTypeService seatTypeService;
    private final SeatService seatService;
    private final ScreenService screenService;
    private final TheaterService theaterService;


    @GetMapping("/get-seat-types")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<SeatTypeResponseDto> getSeatTypes(){
        return  seatTypeService.getSeatTypes();
    }

    @PostMapping("/add-seat-type")
    public void addSeatType(@RequestBody @Valid SeatTypeRequestDto seatType){
         seatTypeService.addSeatType(seatType);
    }
    @DeleteMapping("/delete-seat-type/{seatTypeId}")
    public void deleteSeatType(@PathVariable Integer seatTypeId){
        seatTypeService.deleteSeatType(seatTypeId);
    }



    @GetMapping("/get-screen-seats/{screenId}")
    public List<?> getScreenSeats(@PathVariable Integer screenId){
        return seatService.getScreenSeats(screenId);
    }

    @PostMapping("add-screen-seat")
    public void addScreenSeat(@RequestBody @Valid SeatRequestDto seatRequestDto){
            seatService.addScreenSeat(seatRequestDto);
    }

    @DeleteMapping("seat/{seatId}")
    public void deleteSeat(@PathVariable Integer seatId){
         seatService.deleteSeat(seatId);
    }

    @GetMapping("/{theaterId}/screens")
    public List<?> getScreens(@PathVariable Integer theaterId){
        return screenService.getScreens(theaterId);
    }
    @PostMapping("/add-screen")
    public void addScreen(@RequestBody @Valid ScreenRequestDto screenRequestDto){
        screenService.addScreen(screenRequestDto);
    }
    @DeleteMapping("screen/{screenId}")
    public void deleteScreen(@PathVariable Integer screenId){
        screenService.deleteScreen(screenId);
    }

    @GetMapping("/get-theaters")
    public List<?> getTheaters(){
        return theaterService.getTheaters();
    }

    @PostMapping("/add-theater")
    public void addTheater(@RequestBody @Valid TheaterRequestDto theaterRequestDto){
        theaterService.addTheater(theaterRequestDto);
    }

    @DeleteMapping("/delete-theater/{theaterId}")
    public void deleteTheater(@PathVariable Integer theaterId){
        theaterService.deleteTheater(theaterId);
    }

}
