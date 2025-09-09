package com.gr74.ScreenMaster.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "Showtime ID is required")
    private Integer showtimeId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Seat selection is required")
    private List<Integer> seatIds;

//    @Min(value = 1, message = "Total amount must be greater than zero")
//    private int totalAmount;
}
