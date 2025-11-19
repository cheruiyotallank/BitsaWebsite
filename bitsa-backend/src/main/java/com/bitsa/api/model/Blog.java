package com.bitsa.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 20000)
    private String content;

    private String imageUrl;

    private Instant createdAt = Instant.now();

    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @Transient
    private String authorName;
}
