package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.model.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {

    List<BookingSeat> findByBookingId(int bookingId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT bs FROM BookingSeat bs JOIN bs.booking b WHERE b.showtime.id = ?1 AND bs.seat.id IN ?2")
    List<BookingSeat> findByShowtimeIdAndSeatIdsWithLock(int showtimeId, List<Integer> seatIds);

    @Query("SELECT bs FROM BookingSeat bs JOIN bs.booking b WHERE b.showtime.id = ?1 AND bs.seat.id IN ?2")
    List<BookingSeat> findByShowtimeIdAndSeatIds(int showtimeId, List<Integer> seatIds);

    @Query("SELECT COUNT(bs) > 0 FROM BookingSeat bs JOIN bs.booking b WHERE b.showtime.id = ?1 AND bs.seat.id = ?2")
    boolean existsByShowtimeIdAndSeatId(int showtimeId, int seatId);

    @Query("SELECT COUNT(bs) > 0 FROM BookingSeat bs JOIN bs.booking b WHERE b.showtime.id = ?1 AND bs.seat.id IN ?2")
    boolean existsByShowtimeIdAndSeatIdIn(int showtimeId, List<Integer> seatIds);

//    @Query("SELECT bs FROM BookingSeat bs JOIN bs.booking b " +
//           "WHERE b.showtime.id = ?1 AND bs.seat.id IN ?2 AND b.bookingStatus <> 'CANCELLED'")
//    List<BookingSeat> findActiveBookingsByShowtimeIdAndSeatIds(int showtimeId, List<Integer> seatIds);

    @Query("SELECT COUNT(bs) FROM BookingSeat bs JOIN bs.booking b " +
            "WHERE b.showtime.id = ?1 AND bs.seat.id IN ?2 AND b.bookingStatus <> com.gr74.ScreenMaster.enums.BookingStatus.CANCELLED")
    long countActiveBookingsByShowtimeIdAndSeatIdIn(int showtimeId, List<Integer> seatIds);

}
