package com.pranav.microservices.chatapp_backend.repository;

import com.pranav.microservices.chatapp_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
