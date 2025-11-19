package com.bitsa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * EventRegistrationDTO - Safe event registration response object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistrationDTO {
    private Long id;
    private Long eventId;
    private UserDTO user;
    private String status;
    private Instant registeredAt;
    private Instant attendedAt;
    private String notes;
}
