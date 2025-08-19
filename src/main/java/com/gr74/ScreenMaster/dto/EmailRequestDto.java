package com.gr74.ScreenMaster.dto;

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
public class EmailRequestDto {

    @NotEmpty(message = "Recipient email is required")
    @NotNull(message = "Recipient email is required")
    @Email(message = "Please provide a valid email address")
    private String to;

//    @NotEmpty(message = "Email subject is required")
//    @NotNull(message = "Email subject is required")
    private String subject;

//    @NotEmpty(message = "Email message is required")
//    @NotNull(message = "Email message is required")
    private String message;



    private String userName;


    private String verificationCode;


    private String verificationUrl;







}
