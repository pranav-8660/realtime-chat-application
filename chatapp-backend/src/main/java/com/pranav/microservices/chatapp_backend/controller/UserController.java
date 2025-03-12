package com.pranav.microservices.chatapp_backend.controller;


import com.pranav.microservices.chatapp_backend.model.User;
import com.pranav.microservices.chatapp_backend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value="/get-users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping(value = "/add-user")
    public void addUser(@RequestBody User user){
        userRepository.save(user);
    }
}
