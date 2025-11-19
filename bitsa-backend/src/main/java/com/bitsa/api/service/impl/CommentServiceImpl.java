package com.bitsa.api.service.impl;

import com.bitsa.api.model.Blog;
import com.bitsa.api.model.Comment;
import com.bitsa.api.repository.BlogRepository;
import com.bitsa.api.repository.CommentRepository;
import com.bitsa.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Comment> getCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogIdOrderByCreatedAtDesc(blogId);
        comments.forEach(c -> {
            if (c.getAuthor() != null) c.setAuthorName(c.getAuthor().getName());
        });
        return comments;
    }

    @Override
    public Comment addComment(Long blogId, Comment comment) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found"));
        comment.setBlog(blog);
        if (comment.getCreatedAt() == null) comment.setCreatedAt(Instant.now());
        if (comment.getUpdatedAt() == null) comment.setUpdatedAt(Instant.now());
        Comment saved = commentRepository.save(comment);
        if (saved.getAuthor() != null) saved.setAuthorName(saved.getAuthor().getName());
        return saved;
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(Long commentId, Comment updates) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        if (updates.getContent() != null) comment.setContent(updates.getContent());
        comment.setUpdatedAt(Instant.now());
        Comment saved = commentRepository.save(comment);
        if (saved.getAuthor() != null) saved.setAuthorName(saved.getAuthor().getName());
        return saved;
    }
}
