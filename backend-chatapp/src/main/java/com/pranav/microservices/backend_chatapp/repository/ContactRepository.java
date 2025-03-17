package com.pranav.microservices.backend_chatapp.repository;

import com.pranav.microservices.backend_chatapp.model.Contact;
import com.pranav.microservices.backend_chatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // ✅ Check if a contact already exists between two users
    boolean existsByUserAndContact(User user, User contact);

    // ✅ Fetch contacts for a given user
    List<Contact> findByUser_Username(String username);
}
