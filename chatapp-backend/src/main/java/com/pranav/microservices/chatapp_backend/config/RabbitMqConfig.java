package com.pranav.microservices.chatapp_backend.config;

//queue is from aqmp i.e the rabbitmq queue not util.queue
import org.springframework.amqp.core.Queue;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue chatQueue(){
        return new Queue("chat.queue",true);
    }
}
