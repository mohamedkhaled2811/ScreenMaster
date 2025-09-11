package com.gr74.ScreenMaster.dto.response;


import com.gr74.ScreenMaster.model.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class VerificationEmailDto {

    private String to;
    private String code;
    private String verificationUrl;


}
