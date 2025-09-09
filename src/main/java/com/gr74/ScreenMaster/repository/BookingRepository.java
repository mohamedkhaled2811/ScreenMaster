package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBookingReference(String bookingReference);
    List<Booking> findByUserId(int userId);
    List<Booking> findByShowtimeId(int showtimeId);
}
