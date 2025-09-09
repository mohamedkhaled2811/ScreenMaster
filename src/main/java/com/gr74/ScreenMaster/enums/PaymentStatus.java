package com.gr74.ScreenMaster.enums;


import lombok.Getter;

public enum PaymentStatus {

    CREATED("created"),
    APPROVED("approved"),
    COMPLETED("completed"),
    FAILED("failed"),
    CANCELLED("cancelled");


    @Getter
    private final String status;

    PaymentStatus(String status){
        this.status = status;

    }
}
