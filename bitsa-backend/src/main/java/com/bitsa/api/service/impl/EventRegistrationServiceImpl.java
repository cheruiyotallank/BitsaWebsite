package com.bitsa.api.service.impl;

import com.bitsa.api.model.Event;
import com.bitsa.api.model.EventRegistration;
import com.bitsa.api.model.User;
import com.bitsa.api.repository.EventRegistrationRepository;
import com.bitsa.api.repository.EventRepository;
import com.bitsa.api.repository.UserRepository;
import com.bitsa.api.service.EventRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationServiceImpl implements EventRegistrationService {

    @Autowired
    private EventRegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public EventRegistration registerForEvent(Long eventId, Long userId, String notes) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<EventRegistration> existing = registrationRepository.findByEventIdAndUserId(eventId, userId);
        if (existing.isPresent()) {
            throw new RuntimeException("User already registered for this event");
        }

        EventRegistration registration = EventRegistration.builder()
                .event(event)
                .user(user)
                .status("REGISTERED")
                .registeredAt(Instant.now())
                .notes(notes)
                .build();

        EventRegistration saved = registrationRepository.save(registration);
        setTransientFields(saved);
        return saved;
    }

    @Override
    public EventRegistration updateRegistration(Long registrationId, String status, String notes) {
        EventRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        if (status != null) {
            if (!status.matches("REGISTERED|ATTENDED|CANCELLED")) {
                throw new RuntimeException("Invalid status. Must be REGISTERED, ATTENDED, or CANCELLED");
            }
            registration.setStatus(status);
            if ("ATTENDED".equals(status)) {
                registration.setAttendedAt(Instant.now());
            }
        }
        if (notes != null) registration.setNotes(notes);

        EventRegistration saved = registrationRepository.save(registration);
        setTransientFields(saved);
        return saved;
    }

    @Override
    public void cancelRegistration(Long registrationId) {
        EventRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        registration.setStatus("CANCELLED");
        registrationRepository.save(registration);
    }

    @Override
    public List<EventRegistration> getEventRegistrations(Long eventId) {
        List<EventRegistration> registrations = registrationRepository.findByEventId(eventId);
        registrations.forEach(this::setTransientFields);
        return registrations;
    }

    @Override
    public List<EventRegistration> getUserRegistrations(Long userId) {
        List<EventRegistration> registrations = registrationRepository.findByUserId(userId);
        registrations.forEach(this::setTransientFields);
        return registrations;
    }

    @Override
    public Long getRegistrationCount(Long eventId) {
        return registrationRepository.countRegisteredByEventId(eventId);
    }

    @Override
    public boolean isUserRegistered(Long eventId, Long userId) {
        return registrationRepository.findByEventIdAndUserId(eventId, userId).isPresent();
    }

    private void setTransientFields(EventRegistration registration) {
        if (registration.getUser() != null) {
            registration.setUserName(registration.getUser().getName());
            registration.setUserEmail(registration.getUser().getEmail());
        }
    }
}
