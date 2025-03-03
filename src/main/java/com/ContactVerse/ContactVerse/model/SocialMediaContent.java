package com.ContactVerse.ContactVerse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "social_media_content", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaContent extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;
    private String platform;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
    private String sender;
    private String recipient;
    @OneToMany(mappedBy = "socialMediaContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialMediaAdditionalData> additionalData;

}
