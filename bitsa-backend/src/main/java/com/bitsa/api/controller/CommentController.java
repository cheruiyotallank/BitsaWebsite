package com.bitsa.api.controller;

import com.bitsa.api.model.Comment;
import com.bitsa.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs/{blogId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlogId(blogId));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comment> addComment(@PathVariable Long blogId, @RequestBody Comment comment, @AuthenticationPrincipal UserDetails userDetails) {
        // Note: In a real app, you'd fetch the authenticated user from userDetails
        // For now, the comment author must be set by the client
        return ResponseEntity.ok(commentService.addComment(blogId, comment));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comment> updateComment(@PathVariable Long blogId, @PathVariable Long commentId, @RequestBody Comment updates) {
        return ResponseEntity.ok(commentService.updateComment(commentId, updates));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER') or @commentOwnerCheck.isOwner(#commentId, authentication.principal.username)")
    public ResponseEntity<Void> deleteComment(@PathVariable Long blogId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
