package com.gr74.ScreenMaster.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="booking_seats",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_booking_seat_unique",
                columnNames = {"booking_id", "seat_id"})})

@EntityListeners(AuditingEntityListener.class)
public class BookingSeat {
    @Id
    @GeneratedValue
    private int id;


    @CreatedDate
    @Column(name = "reserved_at", nullable = false, updatable = false)
    private LocalDateTime reservedAt;


    private Double seatPrice ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seat_id")
    private Seat seat;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;




}
