package com.gr74.ScreenMaster.enums;


import lombok.Getter;

public enum ScreenType {

    Front_Screen("front_screen"),
    Rear_Projection("rear_projection"),
    Screen_3D("3D_screen");

    @Getter
    private final String type;


    ScreenType(String type) {
        this.type = type;
    }
}
