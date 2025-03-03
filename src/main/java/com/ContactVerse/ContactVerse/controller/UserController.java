package com.ContactVerse.ContactVerse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ContactVerse.ContactVerse.model.User;
import com.ContactVerse.ContactVerse.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Password will be encoded in the service layer

        User registeredUser = userService.saveUser(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(registeredUser.getId());
        responseDTO.setName(registeredUser.getName());
        responseDTO.setEmail(registeredUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());

        return ResponseEntity.ok(responseDTO);
    }
}

// DTOs (Create these classes in a separate package, e.g., com.ContactVerse.ContactVerse.dto)
class UserRegistrationDTO {
    private String name;
    private String email;
    private String password; // Only for registration

    // Getters and setters for all fields
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}

class UserResponseDTO {
    private Long id;
    private String name;
    private String email;

    // Getters and setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}