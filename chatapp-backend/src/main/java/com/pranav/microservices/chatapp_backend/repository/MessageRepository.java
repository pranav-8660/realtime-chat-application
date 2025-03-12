package com.pranav.microservices.chatapp_backend.repository;

import com.pranav.microservices.chatapp_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    public List<Message> findByReceiver(String receiver);
}
