package com.bitsa.api.controller;

import com.bitsa.api.model.GalleryItem;
import com.bitsa.api.repository.GalleryRepository;
import com.bitsa.api.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<GalleryItem>> all() {
        return ResponseEntity.ok(galleryRepository.findAll());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER', 'STUDENT')")
    public ResponseEntity<GalleryItem> upload(@RequestParam("file") MultipartFile file, @RequestParam(required = false) String caption) {
        String filename = fileStorageService.store(file);
        GalleryItem item = GalleryItem.builder()
                .filename(filename)
                .url("/api/gallery/files/" + filename)
                .caption(caption)
                .build();
        galleryRepository.save(item);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<FileSystemResource> serve(@PathVariable String filename) {
        Path path = fileStorageService.load(filename);
        FileSystemResource res = new FileSystemResource(path.toFile());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(res);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        galleryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Temporary endpoint for testing with URLs
    @PostMapping("/url")
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER', 'STUDENT')")
    public ResponseEntity<GalleryItem> createWithUrl(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        String caption = request.get("caption");

        if (url == null || url.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        GalleryItem item = GalleryItem.builder()
                .filename(url.substring(url.lastIndexOf('/') + 1))
                .url(url)
                .caption(caption)
                .build();
        galleryRepository.save(item);
        return ResponseEntity.ok(item);
    }
}
