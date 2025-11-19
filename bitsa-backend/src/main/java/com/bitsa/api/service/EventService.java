package com.bitsa.api.service;

import com.bitsa.api.model.Event;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface EventService {
    List<Event> findAll();
    Event findById(Long id);
    Event create(Event e);
    Event update(Event e);
    void delete(Long id);
    Page<Event> search(String q, int page, int size);
    List<Event> findUpcoming(Instant from);
}
