package com.bitsa.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password; // bcrypt hash

    @Enumerated(EnumType.STRING)
    private Role role;

    private String avatarUrl;

    private Instant createdAt = Instant.now();
}
