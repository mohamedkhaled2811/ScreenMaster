package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.enums.ShowtimeStatus;
import com.gr74.ScreenMaster.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

    List<Showtime> findByMovieId(int movieId);

    List<Showtime> findByScreenId(int screenId);

    List<Showtime> findByStatus(ShowtimeStatus status);

    List<Showtime> findByShowDate(LocalDate date);

    List<Showtime> findByShowDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM Showtime s WHERE s.movie.id = ?1 AND s.showDate >= ?2")
    List<Showtime> findUpcomingShowtimesByMovie(int movieId, LocalDate today);

    Optional<Showtime> findByScreenIdAndMovieIdAndShowDateAndShowTime(
            int screenId, int movieId, LocalDate showDate, LocalTime showTime);

    @Query("SELECT s FROM Showtime s WHERE s.screen.id = :screenId AND s.showDate = :showDate " +
            "AND s.showTime BETWEEN :startTime AND :endTime")
    List<Showtime> findConflictingShowtimes(
            @Param("screenId") Integer screenId,
            @Param("showDate") LocalDate showDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
