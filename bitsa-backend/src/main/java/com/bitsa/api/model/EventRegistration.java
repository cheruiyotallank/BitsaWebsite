package com.bitsa.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "event_registrations", uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    private String status; // REGISTERED, ATTENDED, CANCELLED

    private Instant registeredAt = Instant.now();

    private Instant attendedAt;

    private String notes; // Optional notes from attendee

    @Transient
    private String userName;

    @Transient
    private String userEmail;
}
