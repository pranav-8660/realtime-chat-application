package com.pranav.microservices.backend_chatapp.controller;

import com.pranav.microservices.backend_chatapp.model.Chat;
import com.pranav.microservices.backend_chatapp.model.Message;
import com.pranav.microservices.backend_chatapp.model.User;
import com.pranav.microservices.backend_chatapp.repository.UserRepository;
import com.pranav.microservices.backend_chatapp.service.ChatService;
import com.pranav.microservices.backend_chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Fetch chat history between two users
    @GetMapping("/{sender}/{receiver}")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String sender, @PathVariable String receiver) {

        List<Message> messages = messageService.getChatMessages(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    // ✅ Save a message with validation
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, Object> requestBody) {
        // ✅ Validate request
        if (!requestBody.containsKey("sender") || !requestBody.containsKey("receiver") || !requestBody.containsKey("content")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields: sender, receiver, content"));
        }

        String senderUsername = (String) requestBody.get("sender");
        String receiverUsername = (String) requestBody.get("receiver");
        String content = (String) requestBody.get("content");

        Optional<User> sender = userRepository.findByUsername(senderUsername);
        Optional<User> receiver = userRepository.findByUsername(receiverUsername);

        if (sender.isEmpty() || receiver.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Sender or receiver does not exist"));
        }

        Chat chat = chatService.findChatBetweenUsers(sender.get(), receiver.get());
        if (chat == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "No chat exists between these users"));
        }

        Message message = new Message(chat, sender.get(), receiver.get(), content, LocalDateTime.now());
        messageService.saveMessage(message);

        return ResponseEntity.ok(Map.of("message", "Message sent successfully!", "chatId", chat.getId()));
    }

}
