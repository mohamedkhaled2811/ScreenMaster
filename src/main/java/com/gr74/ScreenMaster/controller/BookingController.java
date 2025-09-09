package com.gr74.ScreenMaster.controller;

import com.gr74.ScreenMaster.dto.request.BookingRequestDto;
import com.gr74.ScreenMaster.enums.BookingStatus;
import com.gr74.ScreenMaster.enums.PaymentStatus;
import com.gr74.ScreenMaster.model.Booking;
import com.gr74.ScreenMaster.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        Booking createdBooking = bookingService.createBooking(bookingRequestDto);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }


    @GetMapping("/reference/{reference}")
    public ResponseEntity<Booking> getBookingByReference(@PathVariable String reference) {
        Booking booking = bookingService.getBookingByReference(reference);
        return ResponseEntity.ok(booking);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable int userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }


    @GetMapping("/showtime/{showtimeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getBookingsByShowtime(@PathVariable int showtimeId) {
        List<Booking> bookings = bookingService.getBookingsByShowtime(showtimeId);
        return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/{bookingId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable int bookingId,
            @RequestParam BookingStatus status) {
        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(updatedBooking);
    }


    @PatchMapping("/{bookingId}/payment")
    public ResponseEntity<Booking> updatePaymentStatus(
            @PathVariable int bookingId,
            @RequestParam PaymentStatus status) {
        Booking updatedBooking = bookingService.updatePaymentStatus(bookingId, status);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

}
