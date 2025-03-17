package com.pranav.microservices.backend_chatapp.config;

import com.pranav.microservices.backend_chatapp.security.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthHandshakeInterceptor authHandshakeInterceptor;

    public WebSocketConfig(AuthHandshakeInterceptor authHandshakeInterceptor) { // ✅ Inject interceptor
        this.authHandshakeInterceptor = authHandshakeInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")  // ✅ WebSocket endpoint
                .setAllowedOrigins("*")  // ✅ Allow frontend connections
                .addInterceptors(authHandshakeInterceptor) // ✅ Secure connection with JWT
                .withSockJS();  // ✅ Enable fallback for browsers
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // ✅ Enables a message broker for real-time messaging
        registry.setApplicationDestinationPrefixes("/app"); // ✅ Prefix for sending messages
    }
}
