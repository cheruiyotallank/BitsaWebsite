package com.bitsa.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"blog_id", "user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private User user;

    private int score; // 1-5 stars

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();
}
