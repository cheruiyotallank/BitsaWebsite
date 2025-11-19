# BITSA Backend API Documentation

## Overview

BITSA Backend API provides comprehensive endpoints for a platform supporting educational content management, event organization, community engagement, and administrative capabilities.

**Base URL:** `http://localhost:8080/api`  
**API Version:** 1.0.0

---

## Authentication

### JWT Token-Based Authentication

All protected endpoints require a valid JWT Bearer token in the `Authorization` header.

```
Authorization: Bearer <jwt_token>
```

**Token Lifetime:** 1 hour  
**Token Format:** JWT with user ID, email, and roles

### Token Acquisition

Users obtain tokens by authenticating with email and password through the Auth endpoints.

---

## Endpoints by Category

### 1. Authentication Endpoints

#### Register User
```
POST /auth/register
Content-Type: application/json

{
  "name": "Allan Cheruiyot",
  "email": "Allan@example.com",
  "password": "SecurePassword123",
  "role": "STUDENT"
}

Response (201 Created):
{
  "message": "Registration successful",
  "user": {
    "id": 1,
    "name": "Allan Cheruiyot",
    "email": "Allan@example.com",
    "role": "STUDENT"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Login
```
POST /auth/login
Content-Type: application/json

{
  "email": "Allan@example.com",
  "password": "SecurePassword123"
}

Response (200 OK):
{
  "message": "Login successful",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "STUDENT"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Verify Token
```
GET /auth/verify
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "valid": true,
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "Allan@example.com",
    "role": "STUDENT"
  }
}
```

---

### 2. Blog Management Endpoints

#### Get All Blogs (Paginated)
```
GET /blogs?page=0&size=10&sort=createdAt,desc

Response (200 OK):
{
  "content": [
    {
      "id": 1,
      "title": "Getting Started with Spring Boot",
      "content": "A comprehensive guide to Spring Boot...",
      "imageUrl": "/api/gallery/files/blog-header.jpg",
      "author": {
        "id": 2,
        "name": "Jane Smith",
        "email": "jane@example.com",
        "role": "LECTURER"
      },
      "createdAt": "2024-01-15T10:30:00",
      "averageRating": 4.5,
      "commentCount": 3
    }
  ],
  "totalPages": 5,
  "totalElements": 50,
  "number": 0,
  "size": 10
}
```

#### Search Blogs
```
GET /blogs/search?q=spring&page=0&size=10

Response (200 OK):
{
  "content": [...],
  "totalPages": 2,
  "totalElements": 15,
  "number": 0,
  "size": 10
}
```

#### Get Blog by ID
```
GET /blogs/{id}

Response (200 OK):
{
  "id": 1,
  "title": "Getting Started with Spring Boot",
  "content": "A comprehensive guide to Spring Boot...",
  "imageUrl": "/api/gallery/files/blog-header.jpg",
  "author": {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane@example.com",
    "role": "LECTURER"
  },
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "averageRating": 4.5,
  "commentCount": 3
}
```

#### Create Blog
```
POST /blogs
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "title": "New Blog Post",
  "content": "This is the blog content..."
}

Response (201 Created):
{
  "id": 2,
  "title": "New Blog Post",
  "content": "This is the blog content...",
  "author": {...},
  "createdAt": "2024-01-20T14:00:00"
}

Authorization: ADMIN, LECTURER, STUDENT
```

#### Update Blog
```
PUT /blogs/{id}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "title": "Updated Blog Title",
  "content": "Updated content..."
}

Response (200 OK):
{...}
```

#### Delete Blog
```
DELETE /blogs/{id}
Authorization: Bearer <jwt_token>

Response (204 No Content)

Authorization: ADMIN, LECTURER
```

#### Upload Blog Image
```
POST /blogs/{id}/image
Authorization: Bearer <jwt_token>
Content-Type: multipart/form-data

Form Data:
- file: <image_file>

Response (200 OK):
{
  "id": 1,
  "title": "Getting Started with Spring Boot",
  "imageUrl": "/api/gallery/files/uploaded-image.jpg",
  ...
}

Authorization: ADMIN, LECTURER, STUDENT
```

---

### 3. Event Management Endpoints

#### Get All Events (Paginated)
```
GET /events?page=0&size=10

Response (200 OK):
{
  "content": [
    {
      "id": 1,
      "title": "Spring Boot Workshop",
      "description": "Learn Spring Boot development...",
      "dateTime": "2024-02-15T14:00:00",
      "location": "Tech Hub, Room 101",
      "imageUrl": "/api/gallery/files/event-banner.jpg",
      "createdBy": {
        "id": 2,
        "name": "Jane Smith",
        "email": "jane@example.com",
        "role": "LECTURER"
      },
      "registrationCount": 25
    }
  ],
  "totalPages": 3,
  "totalElements": 25,
  "number": 0,
  "size": 10
}
```

#### Search Events
```
GET /events/search?q=workshop&page=0&size=10

Searches in: title, description, location

Response (200 OK): Same as Get All Events
```

#### Get Event by ID
```
GET /events/{id}

Response (200 OK):
{...}
```

#### Create Event
```
POST /events
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "title": "React Workshop",
  "description": "Advanced React patterns and hooks",
  "dateTime": "2024-03-01T10:00:00",
  "location": "Innovation Hub"
}

Response (201 Created): {...}

Authorization: ADMIN, LECTURER
```

#### Update Event
```
PUT /events/{id}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{...}

Response (200 OK): {...}
```

#### Delete Event
```
DELETE /events/{id}
Authorization: Bearer <jwt_token>

Response (204 No Content)

Authorization: ADMIN only
```

#### Upload Event Image
```
POST /events/{id}/image
Authorization: Bearer <jwt_token>
Content-Type: multipart/form-data

Response (200 OK): {...}

Authorization: ADMIN, LECTURER
```

---

### 4. Event Registration Endpoints

#### Register for Event
```
POST /events/{eventId}/registrations
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "notes": "Looking forward to this event!"
}

Response (201 Created):
{
  "id": 1,
  "eventId": 1,
  "user": {
    "id": 3,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "STUDENT"
  },
  "status": "REGISTERED",
  "registeredAt": "2024-01-20T15:30:00",
  "notes": "Looking forward to this event!"
}
```

#### Get Event Registrations
```
GET /events/{eventId}/registrations
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Event registrations retrieved",
  "registrations": [
    {
      "id": 1,
      "eventId": 1,
      "user": {...},
      "status": "REGISTERED",
      "registeredAt": "2024-01-20T15:30:00"
    }
  ],
  "success": true
}

Authorization: ADMIN, LECTURER only
```

#### Get Registration Count
```
GET /events/{eventId}/registrations/count

Response (200 OK):
{
  "message": "Registration count retrieved",
  "count": 25,
  "success": true
}
```

#### Update Registration Status
```
PUT /registrations/{registrationId}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "status": "ATTENDED",
  "notes": "Great event!"
}

Response (200 OK): {...}
```

#### Cancel Registration
```
DELETE /registrations/{registrationId}
Authorization: Bearer <jwt_token>

Response (204 No Content)
```

---

### 5. Comment Endpoints

#### Get Comments for Blog
```
GET /blogs/{blogId}/comments

Response (200 OK):
[
  {
    "id": 1,
    "content": "Great article!",
    "author": {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "role": "STUDENT"
    },
    "createdAt": "2024-01-20T16:00:00",
    "updatedAt": "2024-01-20T16:00:00"
  }
]
```

#### Add Comment
```
POST /blogs/{blogId}/comments
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "content": "Very informative post!"
}

Response (201 Created):
{...}

Authorization: Authenticated users only
```

#### Update Comment
```
PUT /blogs/{blogId}/comments/{commentId}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "content": "Updated comment text"
}

Response (200 OK): {...}
```

#### Delete Comment
```
DELETE /blogs/{blogId}/comments/{commentId}
Authorization: Bearer <jwt_token>

Response (204 No Content)

Authorization: ADMIN, LECTURER, or comment author
```

---

### 6. Rating Endpoints

#### Get Average Rating for Blog
```
GET /blogs/{blogId}/ratings/average

Response (200 OK):
{
  "message": "Average rating retrieved",
  "averageRating": 4.5,
  "totalRatings": 20,
  "success": true
}
```

#### Get All Ratings for Blog
```
GET /blogs/{blogId}/ratings

Response (200 OK):
[
  {
    "id": 1,
    "score": 5,
    "userId": 1,
    "userName": "John Doe"
  },
  {
    "id": 2,
    "score": 4,
    "userId": 2,
    "userName": "Jane Smith"
  }
]
```

#### Rate Blog (Add/Update)
```
POST /blogs/{blogId}/ratings
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "score": 5
}

Response (201 Created):
{
  "id": 1,
  "score": 5,
  "userId": 1,
  "userName": "John Doe"
}

Authorization: Authenticated users only
Score: 1-5 (enforced)
Constraint: One rating per user per blog
```

#### Delete Rating
```
DELETE /blogs/{blogId}/ratings/{ratingId}
Authorization: Bearer <jwt_token>

Response (204 No Content)

Authorization: ADMIN only
```

---

### 7. User Management Endpoints (Admin Only)

#### Get All Users
```
GET /users
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Users retrieved successfully",
  "users": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "role": "STUDENT",
      "avatarUrl": "/api/gallery/files/avatar1.jpg",
      "createdAt": "2024-01-01T10:00:00"
    }
  ],
  "success": true
}

Authorization: ADMIN only
```

#### Get User by ID
```
GET /users/{id}
Authorization: Bearer <jwt_token>

Response (200 OK):
{...}

Authorization: ADMIN only
```

#### Update User
```
PUT /users/{id}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "name": "Updated Name",
  "role": "LECTURER"
}

Response (200 OK):
{...}

Authorization: ADMIN only
```

#### Delete User
```
DELETE /users/{id}
Authorization: Bearer <jwt_token>

Response (204 No Content)

Authorization: ADMIN only
```

---

### 8. Gallery Endpoints

#### Upload Image to Gallery
```
POST /gallery
Authorization: Bearer <jwt_token>
Content-Type: multipart/form-data

Form Data:
- file: <image_file>
- caption: "Image description" (optional)

Response (200 OK):
{
  "id": 1,
  "filename": "image-123.jpg",
  "url": "/api/gallery/files/image-123.jpg",
  "caption": "Image description"
}

Authorization: ADMIN, LECTURER, STUDENT
```

#### Create Gallery Item from URL
```
POST /gallery/url
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "url": "https://example.com/image.jpg",
  "caption": "External image"
}

Response (200 OK): {...}

Authorization: ADMIN, LECTURER, STUDENT
```

#### Get All Gallery Items
```
GET /gallery

Response (200 OK):
[
  {
    "id": 1,
    "filename": "image-123.jpg",
    "url": "/api/gallery/files/image-123.jpg",
    "caption": "Image description"
  }
]
```

#### Serve Image File
```
GET /gallery/files/{filename}

Response (200 OK): Binary image file
Content-Type: image/*
```

#### Delete Gallery Item
```
DELETE /gallery/{id}
Authorization: Bearer <jwt_token>

Response (204 No Content)

Authorization: ADMIN, LECTURER
```

---

### 9. Analytics/Stats Endpoints

#### Get Total Users by Role
```
GET /stats/admin/users
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "User statistics retrieved",
  "stats": {
    "ADMIN": 2,
    "LECTURER": 5,
    "STUDENT": 150
  },
  "success": true
}

Authorization: ADMIN only
```

#### Get Blog Statistics
```
GET /stats/admin/blogs
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Blog statistics retrieved",
  "stats": {
    "totalBlogs": 45,
    "totalComments": 230,
    "totalRatings": 180,
    "averageRating": 4.3
  },
  "success": true
}

Authorization: ADMIN only
```

#### Get Event Statistics
```
GET /stats/admin/events
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Event statistics retrieved",
  "stats": {
    "totalEvents": 12,
    "totalRegistrations": 340,
    "averageAttendance": 28.3
  },
  "success": true
}

Authorization: ADMIN only
```

#### Get Top Blogs by Rating
```
GET /stats/admin/top-blogs?limit=5
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Top blogs retrieved",
  "blogs": [
    {
      "id": 1,
      "title": "Best Blog",
      "averageRating": 4.9,
      "commentCount": 25
    }
  ],
  "success": true
}

Authorization: ADMIN only
```

#### Get Top Events by Attendance
```
GET /stats/admin/top-events?limit=5
Authorization: Bearer <jwt_token>

Response (200 OK):
{...}

Authorization: ADMIN only
```

#### Get Role Distribution
```
GET /stats/admin/role-distribution
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Role distribution retrieved",
  "distribution": {
    "ADMIN": "1.3%",
    "LECTURER": "3.3%",
    "STUDENT": "95.3%"
  },
  "success": true
}

Authorization: ADMIN only
```

#### Get Overall Platform Stats
```
GET /stats/admin/overview
Authorization: Bearer <jwt_token>

Response (200 OK):
{
  "message": "Overview retrieved",
  "overview": {
    "totalUsers": 152,
    "totalBlogs": 45,
    "totalEvents": 12,
    "totalComments": 230,
    "totalRatings": 180,
    "engagementScore": 450
  },
  "success": true
}

Authorization: ADMIN only
```

#### API Health Status
```
GET /stats/admin/health

Response (200 OK):
{
  "message": "System is healthy",
  "status": "UP",
  "timestamp": "2024-01-20T17:00:00",
  "success": true
}

No authentication required (public endpoint)
```

---

## User Roles & Authorization

### Role Hierarchy

1. **ADMIN**: Full platform access, user management, content moderation
2. **LECTURER**: Content creation (blogs/events), comment management
3. **STUDENT**: Content consumption, comments, ratings, event registration

### Authorization Matrix

| Endpoint | ADMIN | LECTURER | STUDENT | Public |
|----------|-------|----------|---------|--------|
| Create Blog | ✓ | ✓ | ✓ | - |
| Delete Blog | ✓ | ✓ | - | - |
| Create Event | ✓ | ✓ | - | - |
| Delete Event | ✓ | - | - | - |
| Upload Image | ✓ | ✓ | ✓ | - |
| Add Comment | ✓ | ✓ | ✓ | - |
| Delete Comment | ✓ | ✓ (own) | ✓ (own) | - |
| Rate Blog | ✓ | ✓ | ✓ | - |
| Register Event | ✓ | ✓ | ✓ | - |
| Manage Users | ✓ | - | - | - |
| View Stats | ✓ | - | - | - |
| Get All Blogs | ✓ | ✓ | ✓ | ✓ |
| Search Blogs | ✓ | ✓ | ✓ | ✓ |

---

## Error Handling

### HTTP Status Codes

- **200 OK**: Successful GET/PUT
- **201 Created**: Successful POST (resource created)
- **204 No Content**: Successful DELETE
- **400 Bad Request**: Invalid request parameters
- **401 Unauthorized**: Missing or invalid authentication token
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **409 Conflict**: Constraint violation (e.g., duplicate rating)
- **500 Internal Server Error**: Server error

### Error Response Format

```json
{
  "message": "Error description",
  "status": 400,
  "timestamp": "2024-01-20T17:00:00",
  "success": false
}
```

---

## Pagination

### Parameters

- `page`: Zero-based page index (default: 0)
- `size`: Number of items per page (default: 10, max: 100)
- `sort`: Sort criteria (e.g., `createdAt,desc`)

### Example

```
GET /blogs?page=1&size=20&sort=createdAt,desc
```

---

## Rate Limiting & Performance

- **Requests per minute**: 100
- **File upload max size**: 10 MB
- **Default page size**: 10 items
- **Maximum page size**: 100 items

---

## Swagger/OpenAPI Documentation

Interactive API documentation is available at:

```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

---

## Getting Started

### 1. Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "SecurePass123",
    "role": "STUDENT"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePass123"
  }'
```

### 3. Use Token for Protected Requests
```bash
curl -X GET http://localhost:8080/api/blogs \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

## Support

For issues or questions regarding the API, please contact: support@bitsa.com
