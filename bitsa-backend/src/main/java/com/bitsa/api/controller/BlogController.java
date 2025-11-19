package com.bitsa.api.controller;

import com.bitsa.api.dto.BlogDTO;
import com.bitsa.api.dto.DTOMapper;
import com.bitsa.api.model.Blog;
import com.bitsa.api.model.User;
import com.bitsa.api.service.BlogService;
import com.bitsa.api.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DTOMapper dtoMapper;

    @GetMapping
    public ResponseEntity<List<BlogDTO>> all() {
        List<Blog> blogs = blogService.findAll();
        return ResponseEntity.ok(dtoMapper.toBlogDTOs(blogs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> get(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        return ResponseEntity.ok(dtoMapper.toBlogDTO(blog));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER', 'STUDENT')")
    public ResponseEntity<BlogDTO> create(@RequestBody Blog blog) {
        // For now, create a dummy author if none provided
        if (blog.getAuthor() == null) {
            blog.setAuthor(User.builder().name(blog.getAuthorName() != null ? blog.getAuthorName() : "Anonymous").build());
        }
        Blog savedBlog = blogService.create(blog);
        return ResponseEntity.ok(dtoMapper.toBlogDTO(savedBlog));
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER', 'STUDENT')")
    public ResponseEntity<BlogDTO> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String filename = fileStorageService.store(file);
        Blog blog = blogService.findById(id);
        blog.setImageUrl("/api/gallery/files/" + filename);
        Blog updated = blogService.update(blog);
        return ResponseEntity.ok(dtoMapper.toBlogDTO(updated));
    }

    @GetMapping("/search")
    public ResponseEntity<org.springframework.data.domain.Page<Blog>> search(@RequestParam(value = "q", required = false) String q,
                                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.search(q, page, size));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
