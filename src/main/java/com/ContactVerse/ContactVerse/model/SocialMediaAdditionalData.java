package com.ContactVerse.ContactVerse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaAdditionalData extends BaseModel { // Extends your base model

    @ManyToOne
    @JoinColumn(name = "social_media_content_id")
    private SocialMediaContent socialMediaContent;

    private String dataKey;
    private String dataType; // Store the data type (e.g., "string", "integer", "boolean")
    private String dataValue; // Store the value as a String (you might need conversion)

    // ... other fields if needed
}