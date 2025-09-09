package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Integer> {

    // Find by name (exact match)
    Optional<Genre> findByName(String name);

    // Find by name (case-insensitive, containing)
    List<Genre> findByNameContainingIgnoreCase(String name);


}
