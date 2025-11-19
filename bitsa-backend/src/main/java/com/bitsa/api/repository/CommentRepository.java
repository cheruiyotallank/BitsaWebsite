package com.bitsa.api.repository;

import com.bitsa.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogIdOrderByCreatedAtDesc(Long blogId);
}
