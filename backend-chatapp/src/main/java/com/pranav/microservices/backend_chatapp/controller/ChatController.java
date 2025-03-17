package com.pranav.microservices.backend_chatapp.controller;

import com.pranav.microservices.backend_chatapp.model.Chat;
import com.pranav.microservices.backend_chatapp.model.User;
import com.pranav.microservices.backend_chatapp.repository.UserRepository;
import com.pranav.microservices.backend_chatapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Delete a chat
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Map<String, String>> deleteChat(@PathVariable Long chatId) {
        String result = chatService.deleteChat(chatId);
        Map<String, String> response = new HashMap<>();

        if ("Chat deleted successfully!".equals(result)) {
            response.put("message", "Chat deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Chat not found or could not be deleted");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ✅ Get chat between two users
    @GetMapping("/get/{user1}/{user2}")
    public ResponseEntity<Map<String, Object>> getChatBetweenUsers(@PathVariable String user1, @PathVariable String user2) {
        Optional<User> userA = userRepository.findByUsername(user1);
        Optional<User> userB = userRepository.findByUsername(user2);
        Map<String, Object> response = new HashMap<>();

        // 🛑 Check if users exist
        if (userA.isEmpty() || userB.isEmpty()) {
            response.put("error", "One or both users do not exist");
            return ResponseEntity.badRequest().body(response);
        }

        // ✅ Find chat (if exists)
        Chat chat = chatService.findChatBetweenUsers(userA.get(), userB.get());

        if (chat == null) {
            response.put("chatId", null);  // ✅ Return null instead of error
            response.put("message", "No chat exists, but you can start messaging.");
            return ResponseEntity.ok(response);
        }

        response.put("chatId", chat.getId());
        return ResponseEntity.ok(response);
    }



    // ✅ Start a new chat between two users
    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startChat(@RequestParam String user1, @RequestParam String user2) {
        Optional<User> userA = userRepository.findByUsername(user1);
        Optional<User> userB = userRepository.findByUsername(user2);
        Map<String, Object> response = new HashMap<>();

        // 🛑 Check if users exist
        if (userA.isEmpty() || userB.isEmpty()) {
            response.put("error", "One or both users do not exist");
            return ResponseEntity.badRequest().body(response);
        }

        // ✅ Start a chat and return chatId
        Long chatId = chatService.startChat(user1, user2);
        response.put("message", "Chat started successfully");
        response.put("chatId", chatId);
        return ResponseEntity.ok(response);
    }
}
