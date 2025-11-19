package com.bitsa.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "gallery")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String url;
    private String caption;

    @ManyToOne
    private User uploadedBy;

    private Instant uploadedAt = Instant.now();
}
