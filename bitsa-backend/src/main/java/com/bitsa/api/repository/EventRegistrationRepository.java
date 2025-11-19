package com.bitsa.api.repository;

import com.bitsa.api.model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    List<EventRegistration> findByEventId(Long eventId);
    List<EventRegistration> findByUserId(Long userId);
    Optional<EventRegistration> findByEventIdAndUserId(Long eventId, Long userId);
    @Query("SELECT COUNT(r) FROM EventRegistration r WHERE r.event.id = :eventId AND r.status = 'REGISTERED'")
    Long countRegisteredByEventId(Long eventId);
}
