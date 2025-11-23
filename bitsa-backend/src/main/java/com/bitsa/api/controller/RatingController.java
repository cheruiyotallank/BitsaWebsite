package com.bitsa.api.controller;

import com.bitsa.api.dto.RatingRequest;
import com.bitsa.api.model.Rating;
import com.bitsa.api.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs/{blogId}/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings(@PathVariable Long blogId) {
        return ResponseEntity.ok(ratingService.getRatingsByBlogId(blogId));
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverageRating(@PathVariable Long blogId) {
        Double avg = ratingService.getAverageRating(blogId);
        return ResponseEntity.ok(java.util.Map.of("averageRating", avg != null ? avg : 0.0));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Rating> addOrUpdateRating(@PathVariable Long blogId, @Valid @RequestBody RatingRequest request) {
        // It's more secure to get the user from the authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Or if the name is the user ID string:
        Long userId = Long.parseLong(authentication.getName());

        return ResponseEntity.ok(ratingService.addOrUpdateRating(blogId, userId, request.getScore()));
    }

    @DeleteMapping("/{ratingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }
}
