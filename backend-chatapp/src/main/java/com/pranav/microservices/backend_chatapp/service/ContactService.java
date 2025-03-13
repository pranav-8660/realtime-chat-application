package com.pranav.microservices.backend_chatapp.service;

import com.pranav.microservices.backend_chatapp.model.Contact;
import com.pranav.microservices.backend_chatapp.model.User;
import com.pranav.microservices.backend_chatapp.repository.ContactRepository;
import com.pranav.microservices.backend_chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public String addContact(String username, String contactUsername) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> contactUser = userRepository.findByUsername(contactUsername);

        if (user.isPresent() && contactUser.isPresent()) {
            Contact contact = new Contact(user.get(), contactUser.get());
            contactRepository.save(contact);
            return "Contact added successfully!";
        }
        return "User or Contact not found!";
    }

    public List<Contact> getUserContacts(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(contactRepository::findByUser).orElse(null);
    }
}
