package com.example.backendapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plantName; // Added field for plant name

    // Many plants can be associated with one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
