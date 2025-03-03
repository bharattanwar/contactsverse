package com.ContactVerse.ContactVerse.service;

import com.ContactVerse.ContactVerse.model.Contact;
import com.ContactVerse.ContactVerse.model.User;
import com.ContactVerse.ContactVerse.repository.jpa.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    // Save a new contact
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // Get all contacts for a specific user
    public List<Contact> getContactsByUser(User user) {  // Correct: Accepts User
        return contactRepository.findByUser(user);
    }


    // Get a single contact by ID
    public Optional<Contact> getContactById(Long contactId) {
        return contactRepository.findById(contactId);
    }

    // Update an existing contact
    public Optional<Contact> updateContact(Long contactId, Contact updatedContact) {
        return contactRepository.findById(contactId).map(existingContact -> {
            existingContact.setName(updatedContact.getName());
            existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
            existingContact.setEmail(updatedContact.getEmail());
            existingContact.setSocialLinks(updatedContact.getSocialLinks());
            return contactRepository.save(existingContact);
        });
    }

    // Delete a contact
    public boolean deleteContact(Long contactId) {
        if (contactRepository.existsById(contactId)) {
            contactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }
}
