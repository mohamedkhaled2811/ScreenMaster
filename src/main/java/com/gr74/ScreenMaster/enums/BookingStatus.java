package com.gr74.ScreenMaster.enums;


import lombok.Getter;

public enum BookingStatus {

    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled"),
    EXPIRED("expired");

    @Getter
    private final String status;

    BookingStatus(String status){
        this.status = status;

    }
}
