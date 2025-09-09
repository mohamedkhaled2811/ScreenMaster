package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.enums.PaymentMethod;
import com.gr74.ScreenMaster.enums.PaymentStatus;
import com.gr74.ScreenMaster.model.Booking;
import com.gr74.ScreenMaster.model.PaymentTransaction;
import com.gr74.ScreenMaster.repository.PaymentRepository;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Optional<PaymentTransaction> findByBookingId(Integer bookingId){
        return paymentRepository.findByBookingId(bookingId);
    }


    public Optional<PaymentTransaction> findById(String  Id){
        return paymentRepository.findById(Id);
    }



    public Boolean successfulPaymentExists(Integer bookingId){
        return paymentRepository.existsByBookingIdAndPaymentStatusIn(
                bookingId,
                Arrays.asList(PaymentStatus.APPROVED, PaymentStatus.COMPLETED)
        );
    }


    @Transactional
    public void createPayment(Payment payment, Booking booking){
          PaymentTransaction paymentTransaction = new PaymentTransaction();
          paymentTransaction.setId(payment.getId());
          paymentTransaction.setBooking(booking);
          paymentTransaction.setPaymentMethod(PaymentMethod.valueOf(payment.getPayer().getPaymentMethod().toUpperCase()));
          String total = payment.getTransactions().getFirst().getAmount().getTotal();
          BigDecimal amount = new BigDecimal(total);
          paymentTransaction.setAmount(amount);
          paymentTransaction.setCurrency(payment.getTransactions().getFirst().getAmount().getCurrency());
          paymentTransaction.setTransactionId(payment.getId());
          paymentTransaction.setPaymentStatus(PaymentStatus.CREATED);
          paymentTransaction.setProcessedAt(Instant.now());
          paymentRepository.save(paymentTransaction);
    }


    @Transactional
    public PaymentTransaction recordPayment(PaymentTransaction paymentTransaction) {
        paymentTransaction.setProcessedAt(Instant.now());
        return paymentRepository.save(paymentTransaction);
    }

    @Transactional
    public void updatePaymentStatus(String transactionId, PaymentStatus status) {
        PaymentTransaction paymentTransaction = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for tx: " + transactionId));
        paymentTransaction.setPaymentStatus(status);
        paymentTransaction.setProcessedAt(Instant.now());
        paymentRepository.save(paymentTransaction);
    }

}