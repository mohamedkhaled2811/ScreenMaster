package com.gr74.ScreenMaster.enums;


import lombok.Getter;

public enum CodeType {
    EMAIL_VERIFICATION("email_verification"),
    PASSWORD_RESET("password_reset");


    @Getter
    private final String type;


    CodeType(String type) {
        this.type = type;
    }
}