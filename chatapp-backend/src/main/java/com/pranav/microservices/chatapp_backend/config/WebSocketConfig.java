package com.pranav.microservices.chatapp_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandlerConfig chatWebSocketHandlerConfig; // Use correct handler

    public WebSocketConfig(ChatWebSocketHandlerConfig chatWebSocketHandlerConfig) {
        this.chatWebSocketHandlerConfig = chatWebSocketHandlerConfig;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandlerConfig, "/ws/chat").setAllowedOrigins("*");
    }
}
