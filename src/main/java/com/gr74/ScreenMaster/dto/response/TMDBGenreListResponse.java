package com.gr74.ScreenMaster.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TMDBGenreListResponse {
    private List<TMDBGenreResponse> genres;
}