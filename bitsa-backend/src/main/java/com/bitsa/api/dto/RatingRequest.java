package com.bitsa.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RatingRequest {

    @NotNull(message = "Score cannot be null")
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must be at most 5") // Assuming a 1-5 rating scale
    private Integer score;

    // Getters and Setters
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}