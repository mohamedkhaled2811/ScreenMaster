package com.gr74.ScreenMaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SyncStatus {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String syncType; // "POPULAR", "TOP_RATED", "NOW_PLAYING", etc.

    @Column(name = "last_sync")
    private LocalDateTime lastSync;

    @Column(name = "last_page")
    private Integer lastPage = 1;

    @Column(name = "total_pages")
    private Integer totalPages;

    private String status; // "RUNNING", "COMPLETED", "FAILED"

    @Column(name = "error_message")
    private String errorMessage;

    // Constructors, getters, setters...
}