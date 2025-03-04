package com.ContactVerse.ContactVerse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @ElementCollection
    @CollectionTable(name = "contact_social_links", joinColumns = @JoinColumn(name = "contact_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "link")
    private Map<String, String> socialLinks = new HashMap<>();

    private boolean deleted = false;


}