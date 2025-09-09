package com.gr74.ScreenMaster.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResendRequestDto {

    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
}

