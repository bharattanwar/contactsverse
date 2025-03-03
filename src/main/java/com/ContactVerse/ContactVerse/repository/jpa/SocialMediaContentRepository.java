package com.ContactVerse.ContactVerse.repository.jpa;

import com.ContactVerse.ContactVerse.model.SocialMediaContent;
import com.ContactVerse.ContactVerse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SocialMediaContentRepository extends JpaRepository<SocialMediaContent, Long> {
    List<SocialMediaContent> findByUser(User user);

    List<SocialMediaContent> findByUserAndPlatform(User user, String platform); // Correct: User object
}