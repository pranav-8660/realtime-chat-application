package com.pranav.microservices.backend_chatapp.repository;

import com.pranav.microservices.backend_chatapp.model.Chat;
import com.pranav.microservices.backend_chatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByUser1AndUser2(User user1, User user2);

    List<Chat> findByUser1OrUser2(User user1, User user2);

    @Query("SELECT c FROM Chat c WHERE (c.user1 = :user1 AND c.user2 = :user2) OR (c.user1 = :user2 AND c.user2 = :user1)")
    Chat findChatBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);


}
