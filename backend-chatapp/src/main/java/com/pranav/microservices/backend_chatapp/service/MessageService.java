package com.pranav.microservices.backend_chatapp.service;

import com.pranav.microservices.backend_chatapp.model.Message;
import com.pranav.microservices.backend_chatapp.model.User;
import com.pranav.microservices.backend_chatapp.repository.MessageRepository;
import com.pranav.microservices.backend_chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Corrected Method: Fetch Messages Using `User` Objects
    public List<Message> getChatMessages(String senderUsername, String receiverUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        return messageRepository.findBySenderAndReceiverOrReceiverAndSender(sender, receiver, receiver, sender);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
