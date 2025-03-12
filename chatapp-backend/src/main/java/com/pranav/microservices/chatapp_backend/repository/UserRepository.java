package com.pranav.microservices.chatapp_backend.repository;


import com.pranav.microservices.chatapp_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
