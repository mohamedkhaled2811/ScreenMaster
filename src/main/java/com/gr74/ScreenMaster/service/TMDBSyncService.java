package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.dto.response.TMDBGenreListResponse;
import com.gr74.ScreenMaster.dto.response.TMDBGenreResponse;
import com.gr74.ScreenMaster.dto.response.TMDBMovieListResponse;
import com.gr74.ScreenMaster.dto.response.TMDBMovieResponse;
import com.gr74.ScreenMaster.model.Genre;
import com.gr74.ScreenMaster.model.Movie;
import com.gr74.ScreenMaster.model.SyncStatus;
import com.gr74.ScreenMaster.repository.GenresRepository;
import com.gr74.ScreenMaster.repository.MoviesRepository;
import com.gr74.ScreenMaster.repository.SyncStatusRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TMDBSyncService {


    private final TMDBApiService tmdbApiService;


    private final MoviesRepository movieRepository;

    private final GenresRepository genreRepository;

    private final SyncStatusRepository syncStatusRepository;


    // Rate limiting
    private static final long API_DELAY_MS = 1000; // 4 requests per second (TMDB limit is 40/10s)


    public void syncAllMovieCategories() {
        log.info("Starting full movie sync...");

        // First sync genres
        syncGenres();

        // Then sync different movie categories
        syncMovieCategory("POPULAR", this::syncPopularMovies);
        syncMovieCategory("TOP_RATED", this::syncTopRatedMovies);
        syncMovieCategory("NOW_PLAYING", this::syncNowPlayingMovies);
        syncMovieCategory("UPCOMING", this::syncUpcomingMovies);



        log.info("Full movie sync completed");
    }

    public void syncGenres() {
        log.info("Syncing genres...");

        try {
            TMDBGenreListResponse response = tmdbApiService.getGenres();

            if (response != null && response.getGenres() != null) {
                for (TMDBGenreResponse genreResponse : response.getGenres()) {
                    Genre genre = genreRepository.findById(genreResponse.getId())
                            .orElse(new Genre());

                    genre.setId(genreResponse.getId());
                    genre.setName(genreResponse.getName());

                    genreRepository.save(genre);
                }

                log.info("Synced {} genres", response.getGenres().size());
            }

        } catch (Exception e) {
            log.error("Error syncing genres", e);
        }
    }

    public void syncPopularMovies()  {
        try {
            syncMovieList("POPULAR", tmdbApiService::getPopularMovies);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt(); // Restore interrupted status
            log.error("Interrupt Exception",e);
        }
    }

    public void syncTopRatedMovies()  {
        try {
            syncMovieList("TOP_RATED", tmdbApiService::getTopRatedMovies);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt(); // Restore interrupted status
            log.error("Interrupt Exception",e);
        }
    }

    public void syncNowPlayingMovies()  {
        try {
            syncMovieList("NOW_PLAYING", tmdbApiService::getNowPlayingMovies);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt(); // Restore interrupted status
            log.error("Interrupt Exception",e);
        }

    }

    public void syncUpcomingMovies() {
        try {
            syncMovieList("UPCOMING", tmdbApiService::getUpcomingMovies);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt(); // Restore interrupted status
            log.error("Interrupt Exception",e);
        }
    }

    private void syncMovieCategory(String categoryName, Runnable syncMethod) {
        try {
            updateSyncStatus(categoryName, "RUNNING", null);
            syncMethod.run();
            updateSyncStatus(categoryName, "COMPLETED", null);
        } catch (Exception e) {
            log.error("Error syncing {} movies", categoryName, e);
            updateSyncStatus(categoryName, "FAILED", e.getMessage());
        }
    }

    private void syncMovieList(String syncType, Function<Integer, TMDBMovieListResponse> apiCall) throws InterruptedException {
        log.info("Syncing {} movies...", syncType);

        SyncStatus syncStatus = syncStatusRepository.findBySyncType(syncType)
                .orElse(new SyncStatus());
        syncStatus.setSyncType(syncType);

        int startPage = 1;
        int totalSynced = 0;

        try {

            Integer total_pages = apiCall.apply(1).getTotalPages();
            for (int page = startPage; page <= total_pages; page++) { // Limit to first 10 pages
                log.debug("Fetching {} movies - page {}", syncType, page);

                TMDBMovieListResponse response = apiCall.apply(page);

                if (response == null || response.getResults() == null) {
                    log.warn("No data received for {} movies page {}", syncType, page);
                    break;
                }

                // Update sync status
                syncStatus.setTotalPages(response.getTotalPages());
                syncStatus.setLastPage(page);
                syncStatusRepository.save(syncStatus);

                // Process movies
                for (TMDBMovieResponse movieResponse : response.getResults()) {
                    try {
                        saveOrUpdateMovie(movieResponse);
                        totalSynced++;
                    } catch (Exception e) {
                        log.error("Error saving movie ID {}: {}", movieResponse.getId(), e.getMessage());
                    }
                }

                // Rate limiting
                Thread.sleep(API_DELAY_MS);

                // Stop if we've reached the last page
                if (page >= response.getTotalPages()) {
                    break;
                }
            }

            syncStatus.setLastSync(LocalDateTime.now());
            syncStatusRepository.save(syncStatus);

            log.info("Synced {} {} movies", totalSynced, syncType);

        } catch (Exception e) {
            log.error("Error syncing {} movies", syncType, e);
            throw e;
        }
    }

    private Movie saveOrUpdateMovie(TMDBMovieResponse movieResponse) {
//        Movie movie = movieRepository.findById(movieResponse.getId())
//                .orElse(new Movie());


        Movie movie = new Movie();
        // Map TMDB response to entity
        movie.setId(movieResponse.getId());
        movie.setTitle(movieResponse.getTitle());
        movie.setOverview(movieResponse.getOverview());
        movie.setOriginalTitle(movieResponse.getOriginalTitle());
        movie.setVoteAverage(movieResponse.getVoteAverage());
        movie.setVoteCount(movieResponse.getVoteCount());
        movie.setPopularity(movieResponse.getPopularity());
        movie.setBackdropPath(movieResponse.getBackdropPath());
        movie.setPosterPath(movieResponse.getPosterPath());
        movie.setOriginalLanguage(movieResponse.getOriginalLanguage());
        movie.setAdult(movieResponse.getAdult());

        // Parse release date
        if (movieResponse.getReleaseDate() != null && !movieResponse.getReleaseDate().isEmpty()) {
            try {
                movie.setReleaseDate(LocalDate.parse(movieResponse.getReleaseDate()));
            } catch (Exception e) {
                log.warn("Could not parse release date: {}", movieResponse.getReleaseDate());
            }
        }

        if (movieResponse.getGenreIds() != null && !movieResponse.getGenreIds().isEmpty()) {
            List<Genre> movieGenres = convertGenreIdsToGenres(movieResponse.getGenreIds());
            movie.setGenres(movieGenres);

        }

        return movieRepository.save(movie);
    }


    private List<Genre> convertGenreIdsToGenres(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();

        for (Integer genreId : genreIds) {
            Optional<Genre> genreOpt = genreRepository.findById(genreId);
            if (genreOpt.isPresent()) {
                genres.add(genreOpt.get());
            } else {
                log.warn("Genre with ID {} not found in database", genreId);
                // Optionally, you could create a placeholder genre or fetch it from TMDB
            }
        }

        return genres;
    }


    private void updateSyncStatus(String syncType, String status, String errorMessage) {
        SyncStatus syncStatus = syncStatusRepository.findBySyncType(syncType)
                .orElse(new SyncStatus());

        syncStatus.setSyncType(syncType);
        syncStatus.setStatus(status);
        syncStatus.setErrorMessage(errorMessage);

        if ("COMPLETED".equals(status)) {
            syncStatus.setLastSync(LocalDateTime.now());
        }

        syncStatusRepository.save(syncStatus);
    }
}