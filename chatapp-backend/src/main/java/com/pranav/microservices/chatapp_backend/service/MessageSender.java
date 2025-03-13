package com.pranav.microservices.chatapp_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.microservices.chatapp_backend.model.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public MessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper; // Use Spring's managed ObjectMapper
    }

    public void sendMessage(Message message) {
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend("chat-exchange", "chat-routing-key", messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
