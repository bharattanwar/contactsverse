package com.ContactVerse.ContactVerse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BaseModel implements Serializable {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
