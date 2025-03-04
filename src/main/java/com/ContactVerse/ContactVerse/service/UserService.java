package com.ContactVerse.ContactVerse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ContactVerse.ContactVerse.model.User;
import com.ContactVerse.ContactVerse.repository.jpa.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Use final field

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) { // Constructor injection
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email is already registered");
        }
        if (user.getPassword().length() < 6) { // Example validation
            throw new RuntimeException("Password must be at least 6 characters long");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}