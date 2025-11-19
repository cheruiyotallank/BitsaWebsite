package com.bitsa.api.service;

import com.bitsa.api.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByBlogId(Long blogId);
    Comment addComment(Long blogId, Comment comment);
    void deleteComment(Long commentId);
    Comment updateComment(Long commentId, Comment updates);
}
