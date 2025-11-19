package com.bitsa.api.repository;

import com.bitsa.api.model.Blog;
import com.bitsa.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByAuthor(User author);
    List<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    Page<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
}
