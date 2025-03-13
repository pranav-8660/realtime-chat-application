package com.pranav.microservices.chatapp_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pranav.microservices.chatapp_backend.model.Message;
import com.pranav.microservices.chatapp_backend.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageReceiver {

    private final MessageRepository messageRepository;
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper;

    public MessageReceiver(MessageRepository messageRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule
    }

    @RabbitListener(queues = "chat.queue")
    public void receiveMessage(String messageJson) {
        try {
            // Deserialize JSON message to Message object
            Message message = objectMapper.readValue(messageJson, Message.class);
            System.out.println("Received message: " + message.getContent());

            // Save message to database
            messageRepository.save(message);

            // Broadcast message to all connected WebSocket clients
            broadcastMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send the received message to all connected WebSocket clients
    private void broadcastMessage(Message message) {
        String formattedMessage;
        try {
            formattedMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(formattedMessage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Add WebSocket session
    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    // Remove WebSocket session
    public static void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }
}
