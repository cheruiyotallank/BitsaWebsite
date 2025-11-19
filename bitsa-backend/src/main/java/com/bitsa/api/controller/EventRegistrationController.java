package com.bitsa.api.controller;

import com.bitsa.api.model.EventRegistration;
import com.bitsa.api.service.EventRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventRegistrationController {

    @Autowired
    private EventRegistrationService registrationService;

    /**
     * Register user for an event
     * POST /api/events/{eventId}/register
     */
    @PostMapping("/{eventId}/register")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> registerForEvent(
            @PathVariable Long eventId,
            @RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            String notes = (String) request.getOrDefault("notes", "");

            EventRegistration registration = registrationService.registerForEvent(eventId, userId, notes);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Successfully registered for event",
                    "registration", registration,
                    "success", true
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "success", false
            ));
        }
    }

    /**
     * Get all registrations for an event
     * GET /api/events/{eventId}/registrations
     */
    @GetMapping("/{eventId}/registrations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    public ResponseEntity<Map<String, Object>> getEventRegistrations(@PathVariable Long eventId) {
        List<EventRegistration> registrations = registrationService.getEventRegistrations(eventId);
        Long totalCount = registrationService.getRegistrationCount(eventId);

        return ResponseEntity.ok(Map.of(
                "eventId", eventId,
                "totalRegistrations", totalCount,
                "registrations", registrations,
                "success", true
        ));
    }

    /**
     * Get user's event registrations
     * GET /api/events/user/{userId}/registrations
     */
    @GetMapping("/user/{userId}/registrations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> getUserRegistrations(@PathVariable Long userId) {
        List<EventRegistration> registrations = registrationService.getUserRegistrations(userId);

        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "registrations", registrations,
                "success", true
        ));
    }

    /**
     * Get registration count for an event
     * GET /api/events/{eventId}/registration-count
     */
    @GetMapping("/{eventId}/registration-count")
    public ResponseEntity<Map<String, Object>> getRegistrationCount(@PathVariable Long eventId) {
        Long count = registrationService.getRegistrationCount(eventId);

        return ResponseEntity.ok(Map.of(
                "eventId", eventId,
                "registrationCount", count,
                "success", true
        ));
    }

    /**
     * Check if user is registered for event
     * GET /api/events/{eventId}/user/{userId}/is-registered
     */
    @GetMapping("/{eventId}/user/{userId}/is-registered")
    public ResponseEntity<Map<String, Object>> isUserRegistered(
            @PathVariable Long eventId,
            @PathVariable Long userId) {
        boolean isRegistered = registrationService.isUserRegistered(eventId, userId);

        return ResponseEntity.ok(Map.of(
                "eventId", eventId,
                "userId", userId,
                "isRegistered", isRegistered,
                "success", true
        ));
    }

    /**
     * Update registration (mark as attended, add notes, etc.)
     * PUT /api/events/registrations/{registrationId}
     */
    @PutMapping("/registrations/{registrationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    public ResponseEntity<Map<String, Object>> updateRegistration(
            @PathVariable Long registrationId,
            @RequestBody Map<String, Object> request) {
        try {
            String status = (String) request.getOrDefault("status", null);
            String notes = (String) request.getOrDefault("notes", null);

            EventRegistration updated = registrationService.updateRegistration(registrationId, status, notes);

            return ResponseEntity.ok(Map.of(
                    "message", "Registration updated successfully",
                    "registration", updated,
                    "success", true
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "success", false
            ));
        }
    }

    /**
     * Cancel registration for event
     * DELETE /api/events/registrations/{registrationId}
     */
    @DeleteMapping("/registrations/{registrationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> cancelRegistration(@PathVariable Long registrationId) {
        try {
            registrationService.cancelRegistration(registrationId);

            return ResponseEntity.ok(Map.of(
                    "message", "Registration cancelled successfully",
                    "success", true
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "success", false
            ));
        }
    }
}
