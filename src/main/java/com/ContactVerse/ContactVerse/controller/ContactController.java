package com.ContactVerse.ContactVerse.controller;

import com.ContactVerse.ContactVerse.model.Contact;
import com.ContactVerse.ContactVerse.model.User;
import com.ContactVerse.ContactVerse.service.ContactService;
import com.ContactVerse.ContactVerse.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Validated
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact addContact(@Valid @RequestBody Contact contact) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        contact.setUser(user);

        return contactService.saveContact(contact);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Contact>> getContactsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Contact> contacts = contactService.getContactsByUser(user);
        return contacts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(contacts);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long contactId) {
        return contactService.getContactById(contactId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long contactId, @Valid @RequestBody Contact contact) {
        return contactService.updateContact(contactId, contact)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable Long contactId) {
        return contactService.deleteContact(contactId)
                ? ResponseEntity.ok("Contact deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found");
    }
}