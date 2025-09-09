package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.model.Theater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {

}
