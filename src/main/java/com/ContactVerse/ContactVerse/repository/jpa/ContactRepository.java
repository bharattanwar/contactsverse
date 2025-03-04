package com.ContactVerse.ContactVerse.repository.jpa;

import com.ContactVerse.ContactVerse.model.Contact;
import com.ContactVerse.ContactVerse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUser(User user); // Use User object, not userId
    List<Contact> findByUserAndDeletedFalse(User user);
    Optional<Contact> findByIdAndDeletedFalse(Long contactId);
}