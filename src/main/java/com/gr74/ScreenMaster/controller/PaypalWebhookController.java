package com.gr74.ScreenMaster.controller;

import com.gr74.ScreenMaster.dto.request.PaypalWebhookDto;
import com.gr74.ScreenMaster.enums.BookingStatus;
import com.gr74.ScreenMaster.enums.PaymentStatus;
import com.gr74.ScreenMaster.model.PaymentTransaction;
import com.gr74.ScreenMaster.service.BookingService;
import com.gr74.ScreenMaster.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paypal")
@RequiredArgsConstructor
public class PaypalWebhookController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody PaypalWebhookDto webhookDto) {
        String eventType = webhookDto.getEventType();
        System.out.print(webhookDto);

        if ("PAYMENT.SALE.COMPLETED".equals(eventType)) {
            String parentPayment = webhookDto.getResource().getParentPayment();
            String currency = webhookDto.getResource().getAmount().getCurrency();
            String total = webhookDto.getResource().getAmount().getTotal();

            paymentService.updatePaymentStatus(parentPayment, PaymentStatus.COMPLETED);

            Optional<PaymentTransaction> payment = paymentService.findById(parentPayment);
            Integer bookingId = payment.get().getBooking().getId();

            bookingService.updateBookingStatus(bookingId, BookingStatus.CONFIRMED);

            return ResponseEntity.ok("Payment completed");
        }


        return ResponseEntity.ok("Ignored event: " + eventType);
    }
}