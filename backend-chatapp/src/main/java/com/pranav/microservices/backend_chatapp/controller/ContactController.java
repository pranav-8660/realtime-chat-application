package com.pranav.microservices.backend_chatapp.controller;

import com.pranav.microservices.backend_chatapp.model.Contact;
import com.pranav.microservices.backend_chatapp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/add")
    public String addContact(@RequestParam String username, @RequestParam String contactUsername) {
        return contactService.addContact(username, contactUsername);
    }

    @GetMapping("/{username}")
    public List<Contact> getContacts(@PathVariable String username) {
        return contactService.getUserContacts(username);
    }
}
