package com.gr74.ScreenMaster.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class TMDBScheduledTasks {

    @Autowired
    private TMDBSyncService syncService;


//     Run every day at 2 AM
//    @Scheduled(cron = "0 0 2 * * ?")
//    public void scheduledFullSync() {
//        log.info("Starting scheduled TMDB sync...");
//        try {
//            syncService.syncAllMovieCategories();
//            log.info("Scheduled TMDB sync completed successfully");
//        } catch (Exception e) {
//            log.error("Scheduled TMDB sync failed", e);
//        }
//    }


    @Scheduled(cron = "00 11 14 * * ?")
//    @Scheduled(fixedRate = 14400000) // 4 hours in milliseconds
    public void scheduledMoviesSync() {
        log.info("Starting scheduled popular movies sync...");
        try {
            syncService.syncNowPlayingMovies();
//            syncService.syncPopularMovies();
            log.info("Scheduled popular movies sync completed");
        } catch (Exception e) {
            log.error("Scheduled popular movies sync failed", e);
        }
    }

//    @Scheduled(cron = "20 32 22 * * ?")
//    public void scheduledGenresSync() {
//        log.info("Starting scheduled genres sync...");
//        try {
//            syncService.syncGenres();
//            log.info("Scheduled genres sync completed");
//        } catch (Exception e) {
//            log.error("Scheduled genres sync failed", e);
//        }
//    }

}