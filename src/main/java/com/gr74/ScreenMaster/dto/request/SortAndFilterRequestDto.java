package com.gr74.ScreenMaster.dto.request;

import lombok.Data;

import java.util.List;


@Data
public class SortAndFilterRequestDto {
    private List<SortDto> sort = List.of(
            new SortDto("releaseDate", "desc")  //TODO: not working
    );
    private List<String> genres;
}