package com.example.backendapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name ="plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Added field for plant name
    private String imageUrl;

    // Many plants can be associated with one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors, getters, and setters
    public Plant() {}

    public Plant(String name, String imageUrl, User user) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
