package com.bitsa.api.service;

import java.util.Map;

public interface StatsService {
    Map<String, Object> adminStats();
    Map<String, Object> getUserStats();
    Map<String, Object> getBlogStats();
    Map<String, Object> getEventStats();
    Map<String, Object> getEngagementStats();
    Map<String, Object> getTopBlogsByRating(int limit);
    Map<String, Object> getTopEventsByAttendance(int limit);
    Map<String, Object> getUserRoleDistribution();
    Map<String, Object> getPlatformOverviewStats();
}
