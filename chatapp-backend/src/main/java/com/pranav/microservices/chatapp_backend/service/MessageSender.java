package com.pranav.microservices.chatapp_backend.service;


import com.pranav.microservices.chatapp_backend.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Message message) {
        rabbitTemplate.convertAndSend("chat.queue", message);
    }


}