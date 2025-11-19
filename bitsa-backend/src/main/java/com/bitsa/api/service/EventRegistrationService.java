package com.bitsa.api.service;

import com.bitsa.api.model.EventRegistration;

import java.util.List;

public interface EventRegistrationService {
    EventRegistration registerForEvent(Long eventId, Long userId, String notes);
    EventRegistration updateRegistration(Long registrationId, String status, String notes);
    void cancelRegistration(Long registrationId);
    List<EventRegistration> getEventRegistrations(Long eventId);
    List<EventRegistration> getUserRegistrations(Long userId);
    Long getRegistrationCount(Long eventId);
    boolean isUserRegistered(Long eventId, Long userId);
}
