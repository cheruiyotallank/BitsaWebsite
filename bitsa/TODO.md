# BITSA Comprehensive Testing Plan

## 1. Frontend UI Testing

### 1.1 Authentication & Authorization
- Login page: valid & invalid credentials, error handling
- Register page: all roles (Student, Lecturer, Admin), validation, successful registration and navigation
- Protected routes: access with/without auth, role-based restrictions (e.g., admin-only pages)
- Logout functionality and session persistence

### 1.2 Navigation & Layout
- Navbar links and routing correctness
- Responsive design checks on key pages
- Footer and common component rendering

### 1.3 Page-specific functionality

#### Home
- Check welcome layout, links to other main pages

#### Blogs
- Search functionality with pagination
- Blog list rendering with images, authors, dates
- Navigate to single blog post
- Create blog (Lecturer, Admin, Student roles)
- Edit and delete blog (Admin, Lecturer)
- Upload blog images

#### Blog Detail
- Display content with proper formatting
- Comments loading, adding, editing, and deleting
- Blog rating display (future, as rating is backend-ready)

#### Events
- List and search events with pagination
- Event registration flows (user registration, cancellation)
- Event creation, editing, deletion (Lecturer, Admin)
- Upload event images

#### Gallery
- Upload images/files with caption
- View gallery grid and images
- Delete gallery items (role-based access)

#### Dashboards
- AdminDashboard: show stats, graphs, admin-only features
- LecturerDashboard: event and blog management, approval workflows
- StudentDashboard: registered events, blog interactions

### 1.4 Error/Edge Cases
- Network failures and retry logic
- Unauthorized API calls handling (redirect to login)
- Input validation for all forms and feedback messages

## 2. Backend API Testing (integration/unit)

### 2.1 AuthController
- Login, registration with valid & invalid data
- Profile retrieval with valid and expired tokens

### 2.2 UserController
- CRUD operations by admin, unauthorized access blocked

### 2.3 GalleryController
- File uploads (valid/invalid files)
- File retrieval and deletion permissions

### 2.4 EventController & EventRegistrationController
- Create, update, search, delete events
- Register and unregister users for events
- Access control for admins and users

### 2.5 BlogController & CommentController
- Blog CRUD operations with roles and data validation
- Comment CRUD with ownership and authorization

### 2.6 RatingController
- Rating create, update, average calculation
- Admin rating deletion

### 2.7 StatsController
- Admin-only stats endpoints access
- Public health check endpoint

## 3. Identification of Unused Backend Endpoints
- UserController endpoints not used in frontend
- RatingController frontend usage missing (recommend adding UI or evaluating for removal)
- Verify other endpoints for cleanup potential

## 4. Environment & Deployment Testing
- Validate environment variable configurations for frontend and backend
- Confirm proper CORS settings for deployed domains
- Test frontend build and backend API integration in deployed environment (Netlify, backend server)

## 5. Recommendations
- Implement frontend UI for blog ratings if desired
- Remove or document unused endpoints for clarity
- Continuous integration with tests

---

Please confirm if you want me to proceed with creating test scripts and automated test suites or help with specific testing walkthroughs.
