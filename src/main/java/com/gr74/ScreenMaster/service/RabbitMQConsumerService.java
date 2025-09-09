package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.config.RabbitMQConfig;
import com.gr74.ScreenMaster.dto.response.VerificationEmailDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumerService {

    private final EmailService emailService;


    @RabbitListener(queues = {"${rabbitmq.queue.verificationQueue}"})
    public void consumeVerificationMessage(VerificationEmailDto message) throws MessagingException {

        System.out.println("Received message: " + message.getTo() +" --- "+ message.getCode()+ " --- "+message.getVerificationUrl());
        emailService.sendVerificationEmail(message.getTo(), message.getCode(), message.getVerificationUrl());
    }



}