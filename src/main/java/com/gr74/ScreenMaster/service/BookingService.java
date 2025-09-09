package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.dto.request.BookingRequestDto;
import com.gr74.ScreenMaster.enums.BookingStatus;
import com.gr74.ScreenMaster.enums.PaymentStatus;
import com.gr74.ScreenMaster.model.*;
import com.gr74.ScreenMaster.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final BookingSeatRepository bookingSeatRepository;

    @Transactional
    public Booking createBooking(BookingRequestDto bookingRequestDto) {
        // Fetch user
        User user = userRepository.findById(bookingRequestDto.getUserId()) // TODO: will be deleted because we can get used data from authentication
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Fetch showtime
        Showtime showtime = showtimeRepository.findById(bookingRequestDto.getShowtimeId())
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));

        // Validate seat selection
        List<Integer> seatIds = bookingRequestDto.getSeatIds();
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("Seat selection is required");
        }

        boolean exists = bookingSeatRepository.countActiveBookingsByShowtimeIdAndSeatIdIn(showtime.getId(), seatIds) > 0;
        // Check if any of the selected seats are already booked
        if (exists) {
//        if (bookingSeatRepository.existsActiveBookingsByShowtimeIdAndSeatIdIn(showtime.getId(), seatIds)) {
            throw new RuntimeException("One or more selected seats are already booked");
        }

        // Fetch all seats to calculate the total amount
        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new EntityNotFoundException("One or more selected seats not found");
        }

        // Get the base price from the screen
        double basePrice = showtime.getBasePrice();

        // Calculate total amount based on seat price multipliers
        double totalAmount = seats.stream()
                .mapToDouble(seat -> basePrice * seat.getSeatType().getPriceMultiplier())
                .sum();


        // Create booking
        Booking booking = Booking.builder()
                .bookingReference(generateBookingReference())
                .user(user)
                .showtime(showtime)
                .totalAmount(totalAmount) // Convert to int as per model definition
                .bookingStatus(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.CREATED)
                .bookedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15)) // Booking expires in 15 minutes if not paid
                .build();

        // Save booking to get the ID
        Booking savedBooking = bookingRepository.save(booking);


        // Create and save booking seats
        List<BookingSeat> bookingSeats = seats.stream()
                .map(seat -> BookingSeat.builder()
                        .booking(savedBooking)
                        .seat(seat)
                        .seatPrice(basePrice * seat.getSeatType().getPriceMultiplier())
                        .build()
                ).collect(Collectors.toList());

        bookingSeatRepository.saveAll(bookingSeats);

        return savedBooking;
    }

    public List<Booking> getBookingsByUser(int userId) {
        return bookingRepository.findByUserId(userId);
    }
    public List<Booking> getBookingsByShowtime(int showtimeId) {
        return bookingRepository.findByShowtimeId(showtimeId);
    }

    public Booking getBookingByReference(String reference) {
        return bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

    @Transactional
    public Booking updateBookingStatus(int bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        booking.setBookingStatus(status);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updatePaymentStatus(int bookingId, PaymentStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        booking.setPaymentStatus(status);

        if (status == PaymentStatus.COMPLETED) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
        }

        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    private String generateBookingReference() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }




}







