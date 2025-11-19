package com.bitsa.api.dto;

import com.bitsa.api.model.*;
import com.bitsa.api.repository.CommentRepository;
import com.bitsa.api.repository.EventRegistrationRepository;
import com.bitsa.api.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTOMapper - Utility class to convert entities to DTOs safely
 * Ensures sensitive data like passwords are never exposed in API responses
 */
@Component
public class DTOMapper {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EventRegistrationRepository registrationRepository;

    /**
     * Convert User to UserDTO (excludes password)
     */
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }

    /**
     * Convert list of Users to UserDTOs
     */
    public List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    /**
     * Convert Blog to BlogDTO with engagement metrics
     */
    public BlogDTO toBlogDTO(Blog blog) {
        if (blog == null) return null;
        
        Double avgRating = ratingRepository.getAverageRating(blog.getId());
        List<Comment> comments = commentRepository.findByBlogIdOrderByCreatedAtDesc(blog.getId());
        
        return BlogDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .imageUrl(blog.getImageUrl())
                .author(toUserDTO(blog.getAuthor()))
                .createdAt(blog.getCreatedAt())
                .averageRating(avgRating != null ? avgRating : 0.0)
                .commentCount((long) comments.size())
                .build();
    }

    /**
     * Convert list of Blogs to BlogDTOs
     */
    public List<BlogDTO> toBlogDTOs(List<Blog> blogs) {
        return blogs.stream().map(this::toBlogDTO).collect(Collectors.toList());
    }

    /**
     * Convert Event to EventDTO with registration count
     */
    public EventDTO toEventDTO(Event event) {
        if (event == null) return null;
        
        Long registrationCount = registrationRepository.countRegisteredByEventId(event.getId());
        
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .dateTime(event.getDateTime())
                .location(event.getLocation())
                .imageUrl(event.getImageUrl())
                .createdBy(toUserDTO(event.getCreatedBy()))
                .registrationCount(registrationCount)
                .build();
    }

    /**
     * Convert list of Events to EventDTOs
     */
    public List<EventDTO> toEventDTOs(List<Event> events) {
        return events.stream().map(this::toEventDTO).collect(Collectors.toList());
    }

    /**
     * Convert Comment to CommentDTO
     */
    public CommentDTO toCommentDTO(Comment comment) {
        if (comment == null) return null;
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(toUserDTO(comment.getAuthor()))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    /**
     * Convert list of Comments to CommentDTOs
     */
    public List<CommentDTO> toCommentDTOs(List<Comment> comments) {
        return comments.stream().map(this::toCommentDTO).collect(Collectors.toList());
    }

    /**
     * Convert Rating to RatingDTO
     */
    public RatingDTO toRatingDTO(Rating rating) {
        if (rating == null) return null;
        return RatingDTO.builder()
                .id(rating.getId())
                .score(rating.getScore())
                .userId(rating.getUser() != null ? rating.getUser().getId() : null)
                .userName(rating.getUser() != null ? rating.getUser().getName() : "Unknown")
                .build();
    }

    /**
     * Convert list of Ratings to RatingDTOs
     */
    public List<RatingDTO> toRatingDTOs(List<Rating> ratings) {
        return ratings.stream().map(this::toRatingDTO).collect(Collectors.toList());
    }

    /**
     * Convert EventRegistration to EventRegistrationDTO
     */
    public EventRegistrationDTO toEventRegistrationDTO(EventRegistration registration) {
        if (registration == null) return null;
        return EventRegistrationDTO.builder()
                .id(registration.getId())
                .eventId(registration.getEvent() != null ? registration.getEvent().getId() : null)
                .user(toUserDTO(registration.getUser()))
                .status(registration.getStatus())
                .registeredAt(registration.getRegisteredAt())
                .attendedAt(registration.getAttendedAt())
                .notes(registration.getNotes())
                .build();
    }

    /**
     * Convert list of EventRegistrations to EventRegistrationDTOs
     */
    public List<EventRegistrationDTO> toEventRegistrationDTOs(List<EventRegistration> registrations) {
        return registrations.stream().map(this::toEventRegistrationDTO).collect(Collectors.toList());
    }

    /**
     * Convert GalleryItem to GalleryItemDTO
     */
    public GalleryItemDTO toGalleryItemDTO(GalleryItem item) {
        if (item == null) return null;
        return GalleryItemDTO.builder()
                .id(item.getId())
                .filename(item.getFilename())
                .url(item.getUrl())
                .caption(item.getCaption())
                .uploadedBy(toUserDTO(item.getUploadedBy()))
                .build();
    }

    /**
     * Convert list of GalleryItems to GalleryItemDTOs
     */
    public List<GalleryItemDTO> toGalleryItemDTOs(List<GalleryItem> items) {
        return items.stream().map(this::toGalleryItemDTO).collect(Collectors.toList());
    }
}
