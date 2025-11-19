package com.bitsa.api.service.impl;

import com.bitsa.api.model.Blog;
import com.bitsa.api.repository.BlogRepository;
import com.bitsa.api.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        List<Blog> blogs = blogRepository.findAll();
        blogs.forEach(blog -> {
            if (blog.getAuthor() != null) {
                blog.setAuthorName(blog.getAuthor().getName());
            }
        });
        return blogs;
    }

    @Override
    public Blog findById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow();
        if (blog.getAuthor() != null) {
            blog.setAuthorName(blog.getAuthor().getName());
        }
        return blog;
    }

    @Override
    public Blog create(Blog blog) {
        // Save the author first if it's a new user
        if (blog.getAuthor() != null && blog.getAuthor().getId() == null) {
            // For now, we'll just save the blog with the author as-is
            // In a real app, you'd save the user first
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog update(Blog blog) {
        Blog saved = blogRepository.save(blog);
        if (saved.getAuthor() != null) {
            saved.setAuthorName(saved.getAuthor().getName());
        }
        return saved;
    }

    @Override
    public org.springframework.data.domain.Page<Blog> search(String q, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(Math.max(0, page), Math.max(1, size));
        if (q == null || q.trim().isEmpty()) {
            org.springframework.data.domain.Page<Blog> p = blogRepository.findAll(pageable);
            p.getContent().forEach(b -> { if (b.getAuthor() != null) b.setAuthorName(b.getAuthor().getName()); });
            return p;
        }
        org.springframework.data.domain.Page<Blog> results = blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(q, q, pageable);
        results.getContent().forEach(b -> { if (b.getAuthor() != null) b.setAuthorName(b.getAuthor().getName()); });
        return results;
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
