package com.bitsa.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private User author;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    @Transient
    private String authorName;
}
