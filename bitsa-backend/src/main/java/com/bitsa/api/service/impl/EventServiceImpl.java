package com.bitsa.api.service.impl;

import com.bitsa.api.model.Event;
import com.bitsa.api.repository.EventRepository;
import com.bitsa.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        Instant now = Instant.now();
        return eventRepository.findAll().stream()
                .filter(event -> (event.getStartTime() == null || event.getStartTime().isBefore(now) || event.getStartTime().equals(now)) &&
                                 (event.getEndTime() == null || event.getEndTime().isAfter(now) || event.getEndTime().equals(now)))
                .toList();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Override
    public Event create(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public Event update(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public org.springframework.data.domain.Page<Event> search(String q, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(Math.max(0, page), Math.max(1, size));
        if (q == null || q.trim().isEmpty()) return eventRepository.findAll(pageable);
        return eventRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(q, q, q, pageable);
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findUpcoming(Instant from) {
        return eventRepository.findByDateTimeAfter(from);
    }
}
