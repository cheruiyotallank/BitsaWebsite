package com.bitsa.api.controller;

import com.bitsa.api.model.Event;
import com.bitsa.api.service.EventService;
import com.bitsa.api.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<Event>> all(@RequestParam(value = "upcoming", required = false) Boolean upcoming) {
        if (Boolean.TRUE.equals(upcoming)) {
            return ResponseEntity.ok(eventService.findUpcoming(Instant.now()));
        }
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> get(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    public ResponseEntity<Event> create(@RequestBody Event e) {
        return ResponseEntity.ok(eventService.create(e));
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    public ResponseEntity<Event> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String filename = fileStorageService.store(file);
        Event event = eventService.findById(id);
        event.setImageUrl("/api/gallery/files/" + filename);
        Event updated = eventService.update(event);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<org.springframework.data.domain.Page<Event>> search(@RequestParam(value = "q", required = false) String q,
                                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(eventService.search(q, page, size));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
