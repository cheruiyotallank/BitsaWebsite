package com.bitsa.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private Instant dateTime;

    private Instant startTime;

    private Instant endTime;

    private String location;

    private String imageUrl;

    @ManyToOne
    private User createdBy;
}
