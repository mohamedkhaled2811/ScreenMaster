package com.gr74.ScreenMaster.dto.response;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatTypeRequestDto {


    @NotEmpty(message = "name is mandatory")
    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "priceMultiplier is mandatory")
    private double priceMultiplier;

    @NotEmpty(message = "description must not be empty")
    private String description;

    private boolean is_active;


}
