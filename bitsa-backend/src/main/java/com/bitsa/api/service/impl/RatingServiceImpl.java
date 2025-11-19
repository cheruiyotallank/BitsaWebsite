package com.bitsa.api.service.impl;

import com.bitsa.api.model.Blog;
import com.bitsa.api.model.Rating;
import com.bitsa.api.model.User;
import com.bitsa.api.repository.BlogRepository;
import com.bitsa.api.repository.RatingRepository;
import com.bitsa.api.repository.UserRepository;
import com.bitsa.api.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Rating> getRatingsByBlogId(Long blogId) {
        return ratingRepository.findByBlogId(blogId);
    }

    @Override
    public Rating addOrUpdateRating(Long blogId, Long userId, int score) {
        if (score < 1 || score > 5) throw new RuntimeException("Rating must be between 1 and 5");
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Rating> existing = ratingRepository.findByBlogIdAndUserId(blogId, userId);
        Rating rating;
        if (existing.isPresent()) {
            rating = existing.get();
            rating.setScore(score);
            rating.setUpdatedAt(Instant.now());
        } else {
            rating = Rating.builder()
                    .blog(blog)
                    .user(user)
                    .score(score)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();
        }
        return ratingRepository.save(rating);
    }

    @Override
    public Double getAverageRating(Long blogId) {
        return ratingRepository.getAverageRating(blogId);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
