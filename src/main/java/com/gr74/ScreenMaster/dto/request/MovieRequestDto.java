package com.gr74.ScreenMaster.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieRequestDto {

    private String title;

    private String overview;

    private Boolean adult ;

    private Integer size ;

    private Integer page ;

    private List<SortDto> sortDto;

    private List<String> genres;

}
