package com.bitsa.api.repository;

import com.bitsa.api.model.Rating;
import com.bitsa.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByBlogId(Long blogId);
    Optional<Rating> findByBlogIdAndUserId(Long blogId, Long userId);
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.blog.id = :blogId")
    Double getAverageRating(Long blogId);
}
