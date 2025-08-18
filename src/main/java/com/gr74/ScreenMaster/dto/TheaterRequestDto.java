package com.gr74.ScreenMaster.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TheaterRequestDto {

    @NotEmpty(message = "Theater name is mandatory")
    @NotNull(message = "Theater name is mandatory")
    private String name;

    @NotEmpty(message = "Address is mandatory")
    @NotNull(message = "Address is mandatory")
    private String address;

    @NotEmpty(message = "City is mandatory")
    @NotNull(message = "City is mandatory")
    private String city;

    private boolean is_active = true;
}
