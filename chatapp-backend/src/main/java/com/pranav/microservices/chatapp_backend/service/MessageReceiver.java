package com.pranav.microservices.chatapp_backend.service;

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

    public MessageReceiver(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues = "chat.queue")
    public void receiveMessage(Message message) {
        System.out.println("Received message: " + message.getContent());

        messageRepository.save(message);

        // Send message to all connected WebSocket clients
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message.getSender() + ": " + message.getContent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to add WebSocket sessions (Call this from WebSocket handler)
    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    // Method to remove WebSocket sessions (Call this when a client disconnects)
    public static void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }
}
