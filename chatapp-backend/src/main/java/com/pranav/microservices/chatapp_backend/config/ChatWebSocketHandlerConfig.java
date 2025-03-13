package com.pranav.microservices.chatapp_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatWebSocketHandlerConfig implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandlerConfig.class);
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        logger.info("New WebSocket connection established. Total active sessions: {}", sessions.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        String receivedMessage = message.getPayload().toString();
        logger.info("Received WebSocket message: {}", receivedMessage);

        // Broadcast the message to all active sessions
        broadcastMessage(receivedMessage, session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("WebSocket transport error: ", exception);
        closeSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        logger.info("WebSocket session closed. Status: {} | Active sessions: {}", status, sessions.size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void broadcastMessage(String message, WebSocketSession senderSession) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen() && !session.getId().equals(senderSession.getId())) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    logger.error("Error sending WebSocket message: ", e);
                }
            }
        }
    }

    private void closeSession(WebSocketSession session) {
        try {
            if (session.isOpen()) {
                session.close();
            }
            sessions.remove(session);
        } catch (IOException e) {
            logger.error("Error closing WebSocket session: ", e);
        }
    }
}
