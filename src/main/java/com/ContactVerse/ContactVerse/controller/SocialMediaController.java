package com.ContactVerse.ContactVerse.controller;

import com.ContactVerse.ContactVerse.model.User;
import com.ContactVerse.ContactVerse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.ContactVerse.ContactVerse.model.SocialMediaContent;
import com.ContactVerse.ContactVerse.service.SocialMediaService;
import java.util.List;

@RestController
@RequestMapping("/social-media")
public class SocialMediaController {

    @Autowired
    private SocialMediaService socialMediaService;

    @Autowired
    private UserService userService;

    @GetMapping("/messages/{platform}")
    public ResponseEntity<List<SocialMediaContent>> getMessages(@PathVariable String platform) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<SocialMediaContent> messages = socialMediaService.getMessages(user, platform); // Use the User object
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/add")
    public ResponseEntity<SocialMediaContent> saveContent(@RequestBody SocialMediaContent content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        content.setUser(user); // Set the User object
        return ResponseEntity.ok(socialMediaService.saveContent(content));
    }

    @DeleteMapping("/delete/{contentId}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long contentId) {
        boolean deleted = socialMediaService.deleteContent(contentId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}