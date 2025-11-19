package com.bitsa.api.controller;

import com.bitsa.api.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * StatsController - Professional analytics and statistics endpoints
 * Provides comprehensive platform metrics including user stats, blog engagement,
 * event attendance, and overall platform overview.
 */
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    /**
     * Get overall platform statistics (admin only)
     * GET /api/stats/admin
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.adminStats());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get user statistics
     * GET /api/stats/users
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getUserStats());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get blog engagement statistics
     * GET /api/stats/blogs
     */
    @GetMapping("/blogs")
    public ResponseEntity<Map<String, Object>> getBlogStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getBlogStats());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get event statistics
     * GET /api/stats/events
     */
    @GetMapping("/events")
    public ResponseEntity<Map<String, Object>> getEventStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getEventStats());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get engagement statistics
     * GET /api/stats/engagement
     */
    @GetMapping("/engagement")
    public ResponseEntity<Map<String, Object>> getEngagementStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getEngagementStats());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get top rated blogs
     * GET /api/stats/top-blogs?limit=5
     */
    @GetMapping("/top-blogs")
    public ResponseEntity<Map<String, Object>> getTopBlogs(
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        limit = Math.min(limit, 20);
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getTopBlogsByRating(limit));
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get top attended events
     * GET /api/stats/top-events?limit=5
     */
    @GetMapping("/top-events")
    public ResponseEntity<Map<String, Object>> getTopEvents(
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        limit = Math.min(limit, 20);
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getTopEventsByAttendance(limit));
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get user role distribution
     * GET /api/stats/role-distribution
     */
    @GetMapping("/role-distribution")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserRoleDistribution() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getUserRoleDistribution());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Get comprehensive platform overview
     * GET /api/stats/overview
     */
    @GetMapping("/overview")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getPlatformOverview() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", statsService.getPlatformOverviewStats());
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * GET /api/stats/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
