package com.gr74.ScreenMaster.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${rabbitmq.queue.verificationQueue}")
    public String verificationQueue;

    @Value("${rabbitmq.queue.bookingQueue}")
    private String bookingQueue;

    @Value("${rabbitmq.routing.verificationEmailKey}")
    public String verificationEmailKey;

    @Value("${rabbitmq.routing.bookingKey}")
    private String bookingKey;

    @Value("${rabbitmq.exchange.name}")
    public String exchangeName;

    @Bean
    public Queue verificationQueue() {
        return new Queue(verificationQueue, false);
    }

    @Bean
    public Queue bookingQueue() {
        return new Queue(bookingQueue, false);
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding verificationBinding(Queue verificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(verificationQueue).to(exchange).with(verificationEmailKey);
    }

    @Bean
    public Binding bookingBinding(Queue bookingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(bookingQueue).to(exchange).with(bookingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
    }



}