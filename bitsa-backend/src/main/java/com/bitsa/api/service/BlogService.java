package com.bitsa.api.service;

import com.bitsa.api.model.Blog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {
    List<Blog> findAll();
    Blog findById(Long id);
    Blog create(Blog blog);
    Blog update(Blog blog);
    Page<Blog> search(String q, int page, int size);
    void delete(Long id);
}
