package com.ContactVerse.ContactVerse.service;

import com.ContactVerse.ContactVerse.model.Contact;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SocialMediaService {

    // Validate & auto-generate social media links
    public Map<String, String> validateAndGenerateLinks(Contact contact) {
        Map<String, String> validSocialLinks = new HashMap<>();

        // Validate and store user-provided links
        contact.getSocialLinks().forEach((platform, link) -> {
            if (!isValidUrl(link)) {
                throw new IllegalArgumentException("Invalid URL format for " + platform);
            }
            validSocialLinks.put(platform.toLowerCase(), link);
        });

        // Auto-generate WhatsApp link if missing
        if (!validSocialLinks.containsKey("whatsapp") && contact.getPhoneNumber() != null) {
            validSocialLinks.put("whatsapp", generateWhatsAppLink(contact.getPhoneNumber()));
        }

        return validSocialLinks;
    }

    // Generate a WhatsApp chat link
    private String generateWhatsAppLink(String phoneNumber) {
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", ""); // Remove non-numeric characters
        return "https://wa.me/" + cleanedNumber;
    }

    // Validate URL format
    private boolean isValidUrl(String url) {
        return url.matches("^(http|https)://.*");
    }
}
