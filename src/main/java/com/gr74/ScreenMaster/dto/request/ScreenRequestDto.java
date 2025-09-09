package com.gr74.ScreenMaster.dto.request;

import com.gr74.ScreenMaster.enums.ScreenType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScreenRequestDto {

    @NotEmpty(message = "Screen name is mandatory")
    @NotNull(message = "Screen name is mandatory")
    private String name;

    @NotNull(message = "Total seats is mandatory")
    private Integer totalSeats;

    @NotNull(message = "Screen type is mandatory")
    private ScreenType screenType;

    private boolean is_active = true;

    @NotNull(message = "Theater ID is mandatory")
    private Integer theaterId;
}
