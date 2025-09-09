package com.gr74.ScreenMaster.repository;


import com.gr74.ScreenMaster.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmailAndCodeAndUsedFalse(String email, String code);

    List<VerificationCode> findByEmailAndUsedFalseOrderByCreatedAtDesc(String email);

    @Modifying
    @Query("UPDATE VerificationCode v SET v.used = true WHERE v.email = :email AND v.used = false")
    void markAllAsUsedByEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM VerificationCode v WHERE v.expiresAt < :now")
    void deleteExpiredCodes(@Param("now") LocalDateTime now);

}
