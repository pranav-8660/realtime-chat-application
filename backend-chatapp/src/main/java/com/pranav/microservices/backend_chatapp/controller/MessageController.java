package com.pranav.microservices.backend_chatapp.controller;

import com.pranav.microservices.backend_chatapp.model.Message;
import com.pranav.microservices.backend_chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{chatId}")
    public List<Message> getMessages(@PathVariable Long chatId) {
        return messageService.getChatMessages(chatId);
    }
}
