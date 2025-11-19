package com.bitsa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * CommentDTO - Safe comment response object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String content;
    private UserDTO author;
    private Instant createdAt;
    private Instant updatedAt;
}
