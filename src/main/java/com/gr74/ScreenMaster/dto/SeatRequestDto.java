package com.gr74.ScreenMaster.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatRequestDto {

    @NotNull(message = "Seat number is mandatory")
    private Integer seatNumber;

    @NotNull(message = "Row number is mandatory")
    private String rowNumber;

    private boolean is_available = true;

    private boolean is_accessible = false;

    @NotNull(message = "Seat type id is mandatory")
    private Integer seatTypeId;

    @NotNull(message = "Screen id is mandatory")
    private Integer screenId;

}
