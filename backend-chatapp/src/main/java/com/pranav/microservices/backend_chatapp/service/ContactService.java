package com.pranav.microservices.backend_chatapp.service;

import com.pranav.microservices.backend_chatapp.model.Contact;
import com.pranav.microservices.backend_chatapp.model.User;
import com.pranav.microservices.backend_chatapp.repository.ContactRepository;
import com.pranav.microservices.backend_chatapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public String addContact(String username, String contactUsername) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> contactUser = userRepository.findByUsername(contactUsername);

        if (user.isEmpty() || contactUser.isEmpty()) {
            return "User not found.";
        }

        if (contactRepository.existsByUserAndContact(user.get(), contactUser.get())) {
            return "Contact already exists.";
        }

        Contact contact = new Contact();
        contact.setUser(user.get());
        contact.setContact(contactUser.get());
        contactRepository.save(contact);

        return "Contact added successfully!";
    }

    public List<Contact> getUserContacts(String username) {
        return contactRepository.findByUser_Username(username);
    }
}
