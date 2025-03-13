package com.pranav.microservices.chatapp_backend.controller;

import com.pranav.microservices.chatapp_backend.model.Message;
import com.pranav.microservices.chatapp_backend.repository.MessageRepository;
import com.pranav.microservices.chatapp_backend.service.MessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class MessageController {

    private final MessageSender messageSender;
    private final MessageRepository messageRepository;





    public MessageController(MessageSender messageSender, MessageRepository messageRepository) {
        this.messageSender = messageSender;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/send-message")
    public void sendMessage(@RequestBody Message message){
        message.setTimestamp(LocalDateTime.now());
        messageSender.sendMessage(message);
    }

    @GetMapping("/get-messages/{receiver}")
    public List<Message> getAllMessages(@PathVariable String receiver){
        var logger = LoggerFactory.getLogger(Message.class);
        logger.info("Reciver's name: "+receiver);
        return messageRepository.findByReceiver(receiver);
    }
}
