package com.gr74.ScreenMaster.service;

import com.gr74.ScreenMaster.config.RabbitMQConfig;
import com.gr74.ScreenMaster.dto.response.VerificationEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducerService {

    @Value("${rabbitmq.routing.verificationEmailKey}")
    public String verificationEmailKey;
    @Value("${rabbitmq.exchange.name}")
    public String exchangeName;


    private final RabbitTemplate rabbitTemplate;

    public void sendVerificationMessage(VerificationEmailDto message) {
        rabbitTemplate.convertAndSend(exchangeName, verificationEmailKey, message);
    }



}