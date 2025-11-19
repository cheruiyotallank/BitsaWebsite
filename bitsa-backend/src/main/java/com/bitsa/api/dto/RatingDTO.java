package com.bitsa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RatingDTO - Safe rating response object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private Long id;
    private int score;
    private Long userId;
    private String userName;
}
