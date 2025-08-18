package com.gr74.ScreenMaster.repository;


import com.gr74.ScreenMaster.model.Screen;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {


    List<Screen> findByTheaterId(Integer theaterId);

}
