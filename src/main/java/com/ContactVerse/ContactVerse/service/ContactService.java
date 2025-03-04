package com.ContactVerse.ContactVerse.service;

import com.ContactVerse.ContactVerse.model.Contact;
import com.ContactVerse.ContactVerse.model.User;
import com.ContactVerse.ContactVerse.repository.jpa.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    // Save a new contact for the authenticated user
    public Contact addContactForUser(String userEmail, Contact contact) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    // Get all contacts for a specific user
    public List<Contact> getContactsByUser(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return contactRepository.findByUserAndDeletedFalse(user);
    }

    // Get a single contact by ID
    public Optional<Contact> getContactById(Long contactId) {
        return contactRepository.findByIdAndDeletedFalse(contactId);
    }

    // Update an existing contact
    public Optional<Contact> updateContact(Long contactId, Contact updatedContact) {
        return contactRepository.findByIdAndDeletedFalse(contactId).map(existingContact -> {
            existingContact.setName(updatedContact.getName());
            existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
            existingContact.setEmail(updatedContact.getEmail());
            existingContact.setSocialLinks(updatedContact.getSocialLinks());
            return contactRepository.save(existingContact);
        });
    }

    // Soft delete a contact
    public boolean deleteContact(Long contactId) {
        return contactRepository.findByIdAndDeletedFalse(contactId).map(contact -> {
            contact.setDeleted(true);
            contactRepository.save(contact);
            return true;
        }).orElse(false);
    }
}