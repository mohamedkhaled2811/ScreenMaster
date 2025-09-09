package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.email = :email")
    void enableUserByEmail(@Param("email") String email);
}
