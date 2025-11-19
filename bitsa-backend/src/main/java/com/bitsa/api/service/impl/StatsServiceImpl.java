package com.bitsa.api.service.impl;

import com.bitsa.api.model.Role;
import com.bitsa.api.repository.*;
import com.bitsa.api.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EventRegistrationRepository registrationRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    @Override
    public Map<String, Object> adminStats() {
        Map<String, Object> m = new HashMap<>();
        m.put("users", userRepository.count());
        m.put("events", eventRepository.count());
        m.put("blogs", blogRepository.count());
        return m;
    }

    @Override
    public Map<String, Object> getUserStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("students", userRepository.findAll().stream().filter(u -> u.getRole() == Role.STUDENT).count());
        stats.put("lecturers", userRepository.findAll().stream().filter(u -> u.getRole() == Role.LECTURER).count());
        stats.put("admins", userRepository.findAll().stream().filter(u -> u.getRole() == Role.ADMIN).count());
        return stats;
    }

    @Override
    public Map<String, Object> getBlogStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBlogs", blogRepository.count());
        stats.put("totalComments", commentRepository.count());
        stats.put("totalRatings", ratingRepository.count());
        stats.put("averageBlogRating", getGlobalAverageRating());
        return stats;
    }

    @Override
    public Map<String, Object> getEventStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEvents", eventRepository.count());
        stats.put("totalRegistrations", registrationRepository.count());
        stats.put("averageAttendancePerEvent", getAverageEventAttendance());
        return stats;
    }

    @Override
    public Map<String, Object> getEngagementStats() {
        Map<String, Object> stats = new HashMap<>();
        long totalComments = commentRepository.count();
        long totalRatings = ratingRepository.count();
        long totalRegistrations = registrationRepository.count();
        
        stats.put("totalComments", totalComments);
        stats.put("totalRatings", totalRatings);
        stats.put("totalEventRegistrations", totalRegistrations);
        stats.put("overallEngagementScore", totalComments + totalRatings + totalRegistrations);
        return stats;
    }

    @Override
    public Map<String, Object> getTopBlogsByRating(int limit) {
        Map<String, Object> stats = new HashMap<>();
        List<Map<String, Object>> topBlogs = new ArrayList<>();
        
        blogRepository.findAll().stream()
                .sorted((b1, b2) -> {
                    Double avg1 = ratingRepository.getAverageRating(b1.getId());
                    Double avg2 = ratingRepository.getAverageRating(b2.getId());
                    return Double.compare(avg2 != null ? avg2 : 0, avg1 != null ? avg1 : 0);
                })
                .limit(limit)
                .forEach(blog -> {
                    Map<String, Object> blogData = new HashMap<>();
                    blogData.put("id", blog.getId());
                    blogData.put("title", blog.getTitle());
                    blogData.put("authorName", blog.getAuthor() != null ? blog.getAuthor().getName() : "Anonymous");
                    blogData.put("averageRating", ratingRepository.getAverageRating(blog.getId()));
                    blogData.put("commentCount", commentRepository.findByBlogIdOrderByCreatedAtDesc(blog.getId()).size());
                    topBlogs.add(blogData);
                });
        
        stats.put("topBlogs", topBlogs);
        stats.put("limit", limit);
        return stats;
    }

    @Override
    public Map<String, Object> getTopEventsByAttendance(int limit) {
        Map<String, Object> stats = new HashMap<>();
        List<Map<String, Object>> topEvents = new ArrayList<>();
        
        eventRepository.findAll().stream()
                .sorted((e1, e2) -> {
                    Long count1 = registrationRepository.countRegisteredByEventId(e1.getId());
                    Long count2 = registrationRepository.countRegisteredByEventId(e2.getId());
                    return count2.compareTo(count1);
                })
                .limit(limit)
                .forEach(event -> {
                    Map<String, Object> eventData = new HashMap<>();
                    eventData.put("id", event.getId());
                    eventData.put("title", event.getTitle());
                    eventData.put("location", event.getLocation());
                    eventData.put("registrationCount", registrationRepository.countRegisteredByEventId(event.getId()));
                    eventData.put("dateTime", event.getDateTime());
                    topEvents.add(eventData);
                });
        
        stats.put("topEvents", topEvents);
        stats.put("limit", limit);
        return stats;
    }

    @Override
    public Map<String, Object> getUserRoleDistribution() {
        Map<String, Object> distribution = new HashMap<>();
        long students = userRepository.findAll().stream().filter(u -> u.getRole() == Role.STUDENT).count();
        long lecturers = userRepository.findAll().stream().filter(u -> u.getRole() == Role.LECTURER).count();
        long admins = userRepository.findAll().stream().filter(u -> u.getRole() == Role.ADMIN).count();
        
        distribution.put("STUDENT", students);
        distribution.put("LECTURER", lecturers);
        distribution.put("ADMIN", admins);
        distribution.put("total", students + lecturers + admins);
        return distribution;
    }

    @Override
    public Map<String, Object> getPlatformOverviewStats() {
        Map<String, Object> overview = new HashMap<>();
        overview.put("totalUsers", userRepository.count());
        overview.put("totalBlogs", blogRepository.count());
        overview.put("totalEvents", eventRepository.count());
        overview.put("totalGalleryItems", galleryRepository.count());
        overview.put("totalComments", commentRepository.count());
        overview.put("totalRatings", ratingRepository.count());
        overview.put("totalEventRegistrations", registrationRepository.count());
        overview.put("averageBlogRating", getGlobalAverageRating());
        overview.put("userRoleDistribution", getUserRoleDistribution());
        return overview;
    }

    private Double getGlobalAverageRating() {
        List<Long> blogIds = new ArrayList<>();
        blogRepository.findAll().forEach(b -> blogIds.add(b.getId()));
        
        if (blogIds.isEmpty()) return 0.0;
        
        return blogIds.stream()
                .map(id -> ratingRepository.getAverageRating(id))
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private Double getAverageEventAttendance() {
        long totalEvents = eventRepository.count();
        if (totalEvents == 0) return 0.0;
        
        long totalRegistrations = registrationRepository.count();
        return (double) totalRegistrations / totalEvents;
    }
}

