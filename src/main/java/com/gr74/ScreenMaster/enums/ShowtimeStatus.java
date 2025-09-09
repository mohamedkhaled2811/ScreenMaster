package com.gr74.ScreenMaster.enums;

import lombok.Getter;

public enum ShowtimeStatus {
    SCHEDULED("scheduled"),    // Normal, bookable showtime
    CANCELLED("cancelled"),    // Cancelled by theater
    COMPLETED("completed"),    // Show has finished
    SOLD_OUT ("sold_out");     // All seats booked


    @Getter
    private final String status;

    ShowtimeStatus(String status){
        this.status = status;

    }

}