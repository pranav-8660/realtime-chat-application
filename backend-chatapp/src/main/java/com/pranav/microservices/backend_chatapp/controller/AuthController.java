package com.pranav.microservices.backend_chatapp.controller;

import com.pranav.microservices.backend_chatapp.dto.AuthRequest;
import com.pranav.microservices.backend_chatapp.dto.AuthResponse;
import com.pranav.microservices.backend_chatapp.dto.RegisterRequest;
import com.pranav.microservices.backend_chatapp.service.AuthService;
import com.pranav.microservices.backend_chatapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        String response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
