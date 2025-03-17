package com.pranav.microservices.backend_chatapp.repository;

import com.pranav.microservices.backend_chatapp.model.Message;
import com.pranav.microservices.backend_chatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // ✅ Find messages between two users (regardless of sender/receiver order)
    List<Message> findBySenderAndReceiverOrReceiverAndSender(User sender, User receiver, User receiver2, User sender2);
}
