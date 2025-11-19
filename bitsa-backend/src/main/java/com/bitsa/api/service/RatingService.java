package com.bitsa.api.service;

import com.bitsa.api.model.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> getRatingsByBlogId(Long blogId);
    Rating addOrUpdateRating(Long blogId, Long userId, int score);
    Double getAverageRating(Long blogId);
    void deleteRating(Long ratingId);
}
