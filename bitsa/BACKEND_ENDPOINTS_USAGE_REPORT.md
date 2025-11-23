# Backend Endpoints Usage Report

## Summary

This report presents an analysis of the backend API endpoints usage by the frontend of the BITSA project. It identifies endpoints actively used, unused endpoints, and provides recommendations for maintenance or feature enhancement.

## Backend Endpoints Actively Used in Frontend

- **AuthController**
  - POST /api/auth/login
  - POST /api/auth/register
  - GET /api/auth/profile

- **GalleryController**
  - GET /api/gallery
  - POST /api/gallery
  - DELETE /api/gallery/{id}
  - GET /api/gallery/files/{filename}

- **EventController & EventRegistrationController**
  - GET /api/events/search
  - GET /api/events
  - POST /api/events
  - POST /api/events/{id}/image
  - DELETE /api/events/{id}

- **BlogController**
  - GET /api/blogs/search
  - GET /api/blogs
  - GET /api/blogs/{id}
  - POST /api/blogs
  - POST /api/blogs/{id}/image
  - DELETE /api/blogs/{id}

- **CommentController**
  - Blog comments endpoints (GET, POST, PUT, DELETE)

- **StatsController**
  - GET /api/stats/admin
  - GET /api/stats/blogs
  - GET /api/stats/events

## Backend Endpoints Not Used or Partially Used in Frontend

- **UserController** (All endpoints restricted to admin; no frontend calls detected)
  - GET /api/users
  - GET /api/users/{id}
  - PUT /api/users/{id}
  - DELETE /api/users/{id}

- **RatingController** (No current frontend usage observed)
  - GET /api/blogs/{blogId}/ratings
  - GET /api/blogs/{blogId}/ratings/average
  - POST /api/blogs/{blogId}/ratings
  - DELETE /api/blogs/{blogId}/ratings/{ratingId}

- **StatsController** (Some admin-only endpoints unused in frontend)
  - GET /api/stats/users
  - GET /api/stats/role-distribution
  - GET /api/stats/overview
  - GET /api/stats/health (probably used as a health check)

## Recommendations

1. **Frontend UI Enhancements**
   - Implement blog rating UI to utilize `RatingController` endpoints and engage users in rating blogs.
   - Explore adding frontend administrative UI to manage users if required.

2. **Codebase Maintenance**
   - Document or consider deprecating unused endpoints if they are not planned to be used.
   - Audit admin-only `StatsController` endpoints for current relevance and usage.

3. **Testing**
   - Add test cases covering unused endpoints to ensure they function correctly if kept.
   - Add monitoring or logging of endpoint usage for future maintenance.

## Conclusion

This analysis helps maintain a clean and optimized API surface and guides frontend development priorities to fully utilize backend capabilities.

---

Prepared on [Date].
