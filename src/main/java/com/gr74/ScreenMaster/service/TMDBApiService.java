package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.dto.response.TMDBGenreListResponse;
import com.gr74.ScreenMaster.dto.response.TMDBMovieListResponse;
import com.gr74.ScreenMaster.dto.response.TMDBMovieResponse;
import com.gr74.ScreenMaster.repository.GenresRepository;
import com.gr74.ScreenMaster.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class TMDBApiService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url:https://api.themoviedb.org/3}")
    private String baseUrl;

    private final GenresRepository genersRepository;
    private final MoviesRepository moviesRepository;
    private final WebClient webClient;

    public TMDBMovieListResponse getPopularMovies(int page) {
        String url = String.format("%s/movie/popular?api_key=%s&page=%d", baseUrl, apiKey, page);
        return callApi(url, TMDBMovieListResponse.class);
    }

    public TMDBMovieListResponse getTopRatedMovies(int page) {
        String url = String.format("%s/movie/top_rated?api_key=%s&page=%d", baseUrl, apiKey, page);
        return callApi(url, TMDBMovieListResponse.class);
    }

    public TMDBMovieListResponse getNowPlayingMovies(int page) {
        String url = String.format("%s/movie/now_playing?api_key=%s&page=%d", baseUrl, apiKey, page);
        return callApi(url, TMDBMovieListResponse.class);
    }

    public TMDBMovieListResponse getUpcomingMovies(int page) {
        String url = String.format("%s/movie/upcoming?api_key=%s&page=%d", baseUrl, apiKey, page);
        return callApi(url, TMDBMovieListResponse.class);
    }

    public TMDBMovieResponse getMovieDetails(Long movieId) {
        String url = String.format("%s/movie/%d?api_key=%s", baseUrl, movieId, apiKey);
        return callApi(url, TMDBMovieResponse.class);
    }

    public TMDBGenreListResponse getGenres() {
        String url = String.format("%s/genre/movie/list?api_key=%s", baseUrl, apiKey);
        return callApi(url, TMDBGenreListResponse.class);
    }

    private <T> T callApi(String url, Class<T> responseType) {
        try {
            log.debug("Calling TMDB API: {}", url.replaceAll("api_key=[^&]*", "api_key=***"));

            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(responseType)
                    .timeout(Duration.ofSeconds(10))
                    .block(); // Convert to blocking call for compatibility

        } catch (Exception e) {
            log.error("Error calling TMDB API: {}", url, e);
            throw new RuntimeException("TMDB API call failed", e);
        }
    }
}
