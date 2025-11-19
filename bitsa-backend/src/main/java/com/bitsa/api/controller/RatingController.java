package com.bitsa.api.controller;

import com.bitsa.api.model.Rating;
import com.bitsa.api.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs/{blogId}/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings(@PathVariable Long blogId) {
        return ResponseEntity.ok(ratingService.getRatingsByBlogId(blogId));
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverageRating(@PathVariable Long blogId) {
        Double avg = ratingService.getAverageRating(blogId);
        return ResponseEntity.ok(Map.of("averageRating", avg != null ? avg : 0.0));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Rating> addOrUpdateRating(@PathVariable Long blogId, @RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        Integer score = Integer.parseInt(request.get("score").toString());
        return ResponseEntity.ok(ratingService.addOrUpdateRating(blogId, userId, score));
    }

    @DeleteMapping("/{ratingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRating(@PathVariable Long blogId, @PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }
}
