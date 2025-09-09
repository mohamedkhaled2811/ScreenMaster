package com.gr74.ScreenMaster.controller;


import com.gr74.ScreenMaster.dto.request.ShowtimeRequestDto;
import com.gr74.ScreenMaster.model.Showtime;
import com.gr74.ScreenMaster.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("show-time")
@RequiredArgsConstructor
@Validated
public class ShowTimeController {

    private final ShowtimeService showtimeService;


    @PostMapping
    public ResponseEntity<Showtime> createShowtime(@Valid @RequestBody ShowtimeRequestDto requestDto) {
        Showtime createdShowtime = showtimeService.createShowtime(requestDto);
        return new ResponseEntity<>(createdShowtime, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable int id) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(showtime);
    }


    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Iterable<Showtime>> getShowtimesByMovie(@PathVariable int movieId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByMovie(movieId));
    }

    @GetMapping("/movie/upcoming/{movieId}")
    public ResponseEntity<Iterable<Showtime>> getShowtimesUpcomingByMovie(@PathVariable int movieId) {
        return ResponseEntity.ok(showtimeService.getUpcomingShowtimesByMovie(movieId));
    }



//    @PutMapping("/{id}")
//    public ResponseEntity<Showtime> updateShowtime(
//            @PathVariable int id,
//            @Valid @RequestBody ShowtimeRequestDto requestDto) {
//        Showtime updatedShowtime = showtimeService.updateShowtime(id, requestDto);
//        return ResponseEntity.ok(updatedShowtime);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable int id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }

}
