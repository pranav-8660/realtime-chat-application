package com.pranav.microservices.backend_chatapp.service;

import com.pranav.microservices.backend_chatapp.model.Chat;
import com.pranav.microservices.backend_chatapp.model.User;
import com.pranav.microservices.backend_chatapp.repository.ChatRepository;
import com.pranav.microservices.backend_chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public String deleteChat(Long chatId) {
        if (!chatRepository.existsById(chatId)) {
            return "Chat not found!";
        }
        chatRepository.deleteById(chatId);
        return "Chat deleted successfully!";
    }

    public Chat findChatBetweenUsers(User user1, User user2) {
        return chatRepository.findByUser1AndUser2(user1, user2)
                .orElse(chatRepository.findByUser1AndUser2(user2, user1) // Check both directions
                        .orElse(null));
    }


    public Long startChat(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1)
                .orElseThrow(() -> new RuntimeException("User not found: " + username1));

        User user2 = userRepository.findByUsername(username2)
                .orElseThrow(() -> new RuntimeException("User not found: " + username2));

        // Check if a chat already exists between these users
        Optional<Chat> existingChat = chatRepository.findByUser1AndUser2(user1, user2);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        Chat chat = new Chat(user1, user2, LocalDateTime.now());
        chatRepository.save(chat);
        return chat.getId();  // Return the created chat ID
    }
}
