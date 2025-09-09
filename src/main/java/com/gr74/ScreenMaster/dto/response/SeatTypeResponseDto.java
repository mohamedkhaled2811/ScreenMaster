package com.gr74.ScreenMaster.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatTypeResponseDto {

    private Integer id;
    private String name;
    private double priceMultiplier;
    private String description;
    private boolean is_active;
}
