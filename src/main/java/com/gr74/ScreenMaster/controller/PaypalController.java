package com.gr74.ScreenMaster.controller;


import com.gr74.ScreenMaster.model.Booking;
import com.gr74.ScreenMaster.model.PaymentTransaction;
import com.gr74.ScreenMaster.model.Showtime;
import com.gr74.ScreenMaster.repository.BookingRepository;
import com.gr74.ScreenMaster.service.PaymentService;
import com.gr74.ScreenMaster.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;
    private final PaymentService paymentService;
    private final BookingRepository bookingRepository;


    @GetMapping("/payment/create/{bookingId}")
    public RedirectView createPayment(@PathVariable Integer bookingId){

        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successlUrl = "http://localhost:8080/payment/success";
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new EntityNotFoundException("booking not found"));

            Boolean existingPayment = paymentService.successfulPaymentExists(bookingId);

            if (existingPayment){
                return new RedirectView("/payment/error?already_paid");
            }

            Payment payment = paypalService.createPayment(
                    booking.getTotalAmount(),
            "USD",
                "paypal",
                "sale",
                "payment describtion",
                cancelUrl,
                successlUrl
            );
            System.out.println(payment);
            paymentService.createPayment(payment,booking);

            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }
        }
        catch (PayPalRESTException e){
            log.error("Error occurred:: ",e);
        }
        return new RedirectView("/payment/error");

    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ){
        try {
            Payment payment = paypalService.executePayment(paymentId,payerId);
            if(payment.getState().equals("approved")){
                return "paymentSuccess";
            }
        }
        catch (PayPalRESTException e){
            log.error("Error occurred:: ",e);

        }
        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String patmentCancel(){
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String patmentError(){
        return "paymentError";
    }

}
