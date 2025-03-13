package com.pranav.microservices.backend_chatapp.service;

import com.pranav.microservices.backend_chatapp.model.Chat;
import com.pranav.microservices.backend_chatapp.model.Message;
import com.pranav.microservices.backend_chatapp.repository.ChatRepository;
import com.pranav.microservices.backend_chatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    public List<Message> getChatMessages(Long chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        return chat.map(messageRepository::findByChat).orElse(null);
    }
}
