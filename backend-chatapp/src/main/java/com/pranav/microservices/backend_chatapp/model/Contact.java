package com.pranav.microservices.backend_chatapp.model;


import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // The owner of the contact

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private User contact;  // The contact user

    // Getters & Setters


    public Contact(User user, User contact) {
        this.user = user;
        this.contact = contact;
    }

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }
}
