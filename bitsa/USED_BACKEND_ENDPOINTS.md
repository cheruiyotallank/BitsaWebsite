# Used Backend Endpoints Identified in Frontend

## auth endpoints (from AuthContext.jsx)
- POST /api/auth/login
- POST /api/auth/register
- GET /api/auth/profile

## gallery endpoints (from Gallery.jsx, AdminDashboard.jsx)
- GET /api/gallery
- POST /api/gallery
- DELETE /api/gallery/{id}
- GET /api/gallery/files/{filename}

## events endpoints (from Events.jsx, DashboardLecturer.jsx)
- GET /api/events/search
- GET /api/events
- POST /api/events
- POST /api/events/{id}/image
- DELETE /api/events/{id}

## blog endpoints (from Blogs.jsx, Blog.jsx, DashboardLecturer.jsx, AdminDashboard.jsx)
- GET /api/blogs/search
- GET /api/blogs
- GET /api/blogs/{id}
- POST /api/blogs
- POST /api/blogs/{id}/image
- DELETE /api/blogs/{id}

## stats endpoints (from AdminDashboard.jsx)
- GET /api/stats/admin
- GET /api/stats/blogs
- GET /api/stats/events

## auth headers setting (AuthContext.jsx)
- axios defaults headers Authorization using token

## API Usage Summary:
These endpoints are actively used in the frontend pages and context.

# Next Step:
- Compare this list with backend controller endpoints to identify unused backend endpoints.
