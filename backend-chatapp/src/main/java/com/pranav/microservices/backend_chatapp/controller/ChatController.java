package com.pranav.microservices.backend_chatapp.controller;

import com.pranav.microservices.backend_chatapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @DeleteMapping("/{chatId}")
    public String deleteChat(@PathVariable Long chatId) {
        return chatService.deleteChat(chatId);
    }
}
