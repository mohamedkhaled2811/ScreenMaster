package com.gr74.ScreenMaster.repository;

import com.gr74.ScreenMaster.enums.PaymentStatus;
import com.gr74.ScreenMaster.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentTransaction, String> {
    Optional<PaymentTransaction> findByTransactionId(String transactionId);


    Optional<PaymentTransaction> findByBookingId(Integer bookingId);

    boolean existsByBookingIdAndPaymentStatusIn(Integer bookingId, List<PaymentStatus> statuses);

}