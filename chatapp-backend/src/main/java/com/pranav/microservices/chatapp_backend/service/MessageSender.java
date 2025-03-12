package com.pranav.microservices.chatapp_backend.service;


import com.pranav.microservices.chatapp_backend.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @RabbitListener(queues = "chat.queue")
    public void receiveMessages(Message message) {
        System.out.println("Received Message: " + message.getContent());
    }


}