package com.ContactVerse.ContactVerse.service;

import com.ContactVerse.ContactVerse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ContactVerse.ContactVerse.model.SocialMediaContent;
import com.ContactVerse.ContactVerse.repository.jpa.SocialMediaContentRepository;

import java.util.List;

@Service
public class SocialMediaService {

    @Autowired
    private SocialMediaContentRepository socialMediaContentRepository;

    public List<SocialMediaContent> getMessages(User user, String platform) {  // Correct: Accepts User
        return socialMediaContentRepository.findByUserAndPlatform(user, platform);
    }

    public SocialMediaContent saveContent(SocialMediaContent content) {
        return socialMediaContentRepository.save(content);
    }

    public boolean deleteContent(Long contentId) {
        if (socialMediaContentRepository.existsById(contentId)) {
            socialMediaContentRepository.deleteById(contentId);
            return true;
        }
        return false;
    }
}