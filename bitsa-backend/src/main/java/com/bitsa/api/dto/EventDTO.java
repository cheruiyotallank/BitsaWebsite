package com.bitsa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * EventDTO - Safe event response object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private Instant dateTime;
    private String location;
    private String imageUrl;
    private UserDTO createdBy;
    private Long registrationCount;
}
