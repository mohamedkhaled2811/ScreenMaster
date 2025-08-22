package com.gr74.ScreenMaster.dto.request;

import lombok.Data;

import java.util.List;


@Data
public class SortRequestDto {
    private List<SortDto> sort = List.of(
            new SortDto("releaseDate", "desc")   // ✅ default sort
    ); ;
}