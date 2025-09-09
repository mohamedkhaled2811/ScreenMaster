package com.gr74.ScreenMaster.enums;


import lombok.Getter;

public enum PaymentMethod {

    PAYPAL("paypal"),
    STRIPE("stripe"),
    CARD("card");


    @Getter
    private final String method;

    PaymentMethod(String method){
        this.method = method;

    }
}
