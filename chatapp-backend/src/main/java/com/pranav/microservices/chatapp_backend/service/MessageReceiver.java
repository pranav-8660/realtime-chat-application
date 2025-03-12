package com.pranav.microservices.chatapp_backend.service;

import com.pranav.microservices.chatapp_backend.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    @RabbitListener(queues = "chat.queue")
    public void messageReceiver(Message message)
    {
        System.out.println("Received message: " + message.getContent());
    }
}
