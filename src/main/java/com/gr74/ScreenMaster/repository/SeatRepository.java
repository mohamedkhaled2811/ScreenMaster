package com.gr74.ScreenMaster.repository;



import com.gr74.ScreenMaster.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {


    // Find all seats for a specific screen
    List<Seat> findByScreenId(Integer screenId);


}
