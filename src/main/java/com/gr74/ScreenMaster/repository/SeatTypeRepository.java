package com.gr74.ScreenMaster.repository;



import com.gr74.ScreenMaster.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {



}
