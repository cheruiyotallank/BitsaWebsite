package com.bitsa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * BlogDTO - Safe blog response object with author information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private UserDTO author;
    private Instant createdAt;
    private Double averageRating;
    private Long commentCount;
}
