package com.pranav.microservices.backend_chatapp.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   org.springframework.web.socket.WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String token = servletRequest.getParameter("token");  // ✅ Extract token

            if (token != null) {
                String username = jwtUtil.extractUsername(token);
                if (jwtUtil.validateToken(token, username)) {  // ✅ Validate token
                    attributes.put("username", username);
                    return true;  // ✅ Allow WebSocket connection
                }
            }
        }
        return false;  // ❌ Reject WebSocket connection if token is invalid
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {
        // No action required after handshake
    }
}
