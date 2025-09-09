package com.gr74.ScreenMaster.dto.request;

import com.gr74.ScreenMaster.enums.ShowtimeStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShowtimeRequestDto {

    @NotNull(message = "Movie ID is required")
    private int movieId;

    @NotNull(message = "Screen ID is required")
    private int screenId;

    @NotNull(message = "basePrice is required")
    private int basePrice;

    @NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date must be today or in the future")
    private LocalDate showDate;

    @NotNull(message = "Show time is required")
    private LocalTime showTime;

    private ShowtimeStatus status;

}
