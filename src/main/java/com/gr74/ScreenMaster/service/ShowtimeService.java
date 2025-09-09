package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.dto.request.ShowtimeRequestDto;
import com.gr74.ScreenMaster.enums.ShowtimeStatus;
import com.gr74.ScreenMaster.model.Movie;
import com.gr74.ScreenMaster.model.Screen;
import com.gr74.ScreenMaster.model.Showtime;
import com.gr74.ScreenMaster.repository.MoviesRepository;
import com.gr74.ScreenMaster.repository.ScreenRepository;
import com.gr74.ScreenMaster.repository.ShowtimeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MoviesRepository moviesRepository;
    private final ScreenRepository screenRepository;

    @Transactional
    public Showtime createShowtime(ShowtimeRequestDto showtimeRequestDto) {
        // Check if showtime already exists
        Optional<Showtime> existingShowtime = showtimeRepository.findByScreenIdAndMovieIdAndShowDateAndShowTime(
                showtimeRequestDto.getScreenId(),
                showtimeRequestDto.getMovieId(),
                showtimeRequestDto.getShowDate(),
                showtimeRequestDto.getShowTime()
        );

        if (existingShowtime.isPresent()) {
            throw new EntityExistsException("Showtime already exists for the given screen, movie, date and time");
        }

        LocalTime requestedTime = showtimeRequestDto.getShowTime();
        LocalTime startWindow = requestedTime.minusHours(2).minusMinutes(59);
        LocalTime endWindow = requestedTime.plusHours(2).plusMinutes(59);

        List<Showtime> conflictingShowtimes = showtimeRepository.findConflictingShowtimes(
                showtimeRequestDto.getScreenId(),
                showtimeRequestDto.getShowDate(),
                startWindow,
                endWindow
        );

        if (!conflictingShowtimes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot create showtime. There is already a showtime scheduled within 3 hours " +
                            "of the requested time on the same screen and date"
            );
        }

        Movie movie = moviesRepository.getReferenceById(showtimeRequestDto.getMovieId());
        Screen screen = screenRepository.getReferenceById(showtimeRequestDto.getScreenId());

        Showtime showtime = Showtime.builder()
                .movie(movie)
                .screen(screen)
                .showDate(showtimeRequestDto.getShowDate())
                .showTime(showtimeRequestDto.getShowTime())
                .status(showtimeRequestDto.getStatus() != null ? 
                        showtimeRequestDto.getStatus() : ShowtimeStatus.SCHEDULED)
                .basePrice(showtimeRequestDto.getBasePrice())
                .build();

        return showtimeRepository.save(showtime);
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public Showtime getShowtimeById(int id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));
    }

    public List<Showtime> getShowtimesByMovie(int movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public List<Showtime> getShowtimesByScreen(int screenId) {
        return showtimeRepository.findByScreenId(screenId);
    }

    public List<Showtime> getShowtimesByDate(LocalDate date) {
        return showtimeRepository.findByShowDate(date);
    }

    public List<Showtime> getShowtimesByDateRange(LocalDate startDate, LocalDate endDate) {
        return showtimeRepository.findByShowDateBetween(startDate, endDate);
    }

    public List<Showtime> getUpcomingShowtimesByMovie(int movieId) {
        return showtimeRepository.findUpcomingShowtimesByMovie(movieId, LocalDate.now());
    }

    @Transactional
    public Showtime updateShowtimeStatus(int showtimeId, ShowtimeStatus status) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));

        showtime.setStatus(status);
        return showtimeRepository.save(showtime);
    }

//    @Transactional
//    public Showtime updateShowtime(int showtimeId, ShowtimeRequestDto showtimeRequestDto) {
//        Showtime showtime = showtimeRepository.findById(showtimeId)
//                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));
//
//        // If changing movie, screen, date or time, check for conflicts
//        if (!showtime.getMovie().getId().equals(showtimeRequestDto.getMovieId()) ||
//            !showtime.getScreen().getId().equals(showtimeRequestDto.getScreenId()) ||
//            !showtime.getShowDate().equals(showtimeRequestDto.getShowDate()) ||
//            !showtime.getShowTime().equals(showtimeRequestDto.getShowTime())) {
//
//            Optional<Showtime> existingShowtime = showtimeRepository.findByScreenIdAndMovieIdAndShowDateAndShowTime(
//                    showtimeRequestDto.getScreenId(),
//                    showtimeRequestDto.getMovieId(),
//                    showtimeRequestDto.getShowDate(),
//                    showtimeRequestDto.getShowTime()
//            );

//            if (existingShowtime.isPresent() && existingShowtime.get().getId() != showtimeId) {
//                throw new EntityExistsException("Another showtime already exists for the given screen, movie, date and time");
//            }
//
//            // Fetch movie if changed
//            if (!showtime.getMovie().getId().equals(showtimeRequestDto.getMovieId())) {
//                Movie movie = moviesRepository.findById(showtimeRequestDto.getMovieId())
//                        .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
//                showtime.setMovie(movie);
//            }
//
//            // Fetch screen if changed
//            if (!showtime.getScreen().getId().equals(showtimeRequestDto.getScreenId())) {
//                Screen screen = screenRepository.findById(showtimeRequestDto.getScreenId())
//                        .orElseThrow(() -> new EntityNotFoundException("Screen not found"));
//                showtime.setScreen(screen);
//            }
//        }
//
//        // Update other fields
//        showtime.setShowDate(showtimeRequestDto.getShowDate());
//        showtime.setShowTime(showtimeRequestDto.getShowTime());
//
//        if (showtimeRequestDto.getStatus() != null) {
//            showtime.setStatus(showtimeRequestDto.getStatus());
//        }
//
//        return showtimeRepository.save(showtime);
//    }

    @Transactional
    public void deleteShowtime(int showtimeId) {
        if (!showtimeRepository.existsById(showtimeId)) {
            throw new EntityNotFoundException("Showtime not found");
        }
        showtimeRepository.deleteById(showtimeId);
    }
}
