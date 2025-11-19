# BITSA Backend - Quick Reference Guide

## 🚀 Quick Start

### Prerequisites
- Java 21
- Maven 3.6+
- MySQL 8
- Node.js 18+ (for frontend)

### Building & Running

```bash
# Backend - Build
cd bitsa-backend
mvn clean package

# Backend - Run
mvn spring-boot:run

# Frontend - Install dependencies
cd ../bitsa
npm install

# Frontend - Run
npm run dev
```

**Backend runs on:** `http://localhost:8080`  
**Frontend runs on:** `http://localhost:5173`

---

## 📚 Key Files Location

### Backend Core
- Controllers: `src/main/java/com/bitsa/api/controller/`
- Services: `src/main/java/com/bitsa/api/service/`
- Models: `src/main/java/com/bitsa/api/model/`
- Repositories: `src/main/java/com/bitsa/api/repository/`
- DTOs: `src/main/java/com/bitsa/api/dto/`

### Configuration
- Main Config: `src/main/resources/application.properties`
- CORS Config: `src/main/java/com/bitsa/api/config/CorsConfig.java`
- Security Config: `src/main/java/com/bitsa/api/config/SecurityConfig.java`
- Swagger Config: `src/main/java/com/bitsa/api/config/SwaggerConfig.java`

### Tests
- Unit Tests: `src/test/java/com/bitsa/api/service/`
- Integration Tests: `src/test/java/com/bitsa/api/controller/`

### Documentation
- API Docs: `API_DOCUMENTATION.md`
- Testing Guide: `TESTING_GUIDE.md`
- Implementation Summary: `IMPLEMENTATION_SUMMARY.md`

---

## 🔑 Authentication

### Login Flow
```bash
# 1. Register
POST /api/auth/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "Password123",
  "role": "STUDENT"
}

# 2. Login
POST /api/auth/login
{
  "email": "john@example.com",
  "password": "Password123"
}

# 3. Use token in header
Authorization: Bearer <jwt_token>
```

**Token Lifetime:** 1 hour

---

## 🎯 Core Endpoints

### Blogs
```
GET    /api/blogs                    # List blogs
GET    /api/blogs/search?q=term      # Search blogs
GET    /api/blogs/{id}               # Get blog
POST   /api/blogs                    # Create blog (auth)
PUT    /api/blogs/{id}               # Update blog (auth)
DELETE /api/blogs/{id}               # Delete blog (auth)
POST   /api/blogs/{id}/image         # Upload image (auth)
```

### Events
```
GET    /api/events                   # List events
GET    /api/events/search?q=term     # Search events
GET    /api/events/{id}              # Get event
POST   /api/events                   # Create event (auth)
PUT    /api/events/{id}              # Update event (auth)
DELETE /api/events/{id}              # Delete event (auth)
POST   /api/events/{id}/image        # Upload image (auth)
```

### Registrations
```
POST   /api/events/{eventId}/registrations              # Register
GET    /api/events/{eventId}/registrations              # List registrations (admin/lecturer)
GET    /api/events/{eventId}/registrations/count        # Count
PUT    /api/registrations/{id}                          # Update status
DELETE /api/registrations/{id}                          # Cancel
```

### Comments
```
GET    /api/blogs/{blogId}/comments              # List comments
POST   /api/blogs/{blogId}/comments              # Add comment (auth)
PUT    /api/blogs/{blogId}/comments/{id}         # Update (auth)
DELETE /api/blogs/{blogId}/comments/{id}         # Delete (auth)
```

### Ratings
```
GET    /api/blogs/{blogId}/ratings               # List ratings
GET    /api/blogs/{blogId}/ratings/average       # Average rating
POST   /api/blogs/{blogId}/ratings               # Rate blog (auth)
DELETE /api/blogs/{blogId}/ratings/{id}          # Delete rating (admin)
```

### Admin Functions
```
GET    /api/users                    # List users (admin)
GET    /api/users/{id}               # Get user (admin)
PUT    /api/users/{id}               # Update user (admin)
DELETE /api/users/{id}               # Delete user (admin)
```

### Statistics
```
GET    /api/stats/admin/users                    # User stats
GET    /api/stats/admin/blogs                    # Blog stats
GET    /api/stats/admin/events                   # Event stats
GET    /api/stats/admin/overview                 # Full overview
GET    /api/stats/admin/top-blogs?limit=5        # Top blogs
GET    /api/stats/admin/top-events?limit=5       # Top events
```

---

## 🔐 Role-Based Access

### ADMIN
- Can do everything
- User management
- View all statistics
- Delete any content

### LECTURER
- Create blogs and events
- Delete own blogs, own event comments
- View limited stats
- Can't delete users

### STUDENT
- Create blogs
- Comment on blogs
- Rate blogs
- Register for events
- Cannot delete anything except own comments

---

## 📊 API Response Format

### Success Response
```json
{
  "message": "Operation successful",
  "data": {...},
  "success": true
}
```

### Error Response
```json
{
  "message": "Error description",
  "status": 400,
  "timestamp": "2024-01-20T17:00:00",
  "success": false
}
```

### Paginated Response
```json
{
  "content": [...],
  "totalPages": 5,
  "totalElements": 50,
  "number": 0,
  "size": 10
}
```

---

## ⚙️ Configuration

### Application Properties
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/bitsa_db
spring.datasource.username=root
spring.datasource.password=password

# JWT
jwt.secret=your-secret-key
jwt.expiration=3600000

# File Upload
file.upload-dir=/uploads
```

### Default Pagination
- Page size: 10
- Max page size: 100
- Sort: createdAt, desc

---

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=BlogServiceTests
```

### Run with Coverage
```bash
mvn test jacoco:report
```

Coverage report: `target/site/jacoco/index.html`

### Test Categories
- **Unit Tests**: Service layer logic (32 tests)
- **Integration Tests**: Controller endpoints (22 tests)
- **Total**: 54 tests

---

## 📖 Documentation

### API Documentation
- **Interactive**: http://localhost:8080/swagger-ui.html
- **Raw JSON**: http://localhost:8080/v3/api-docs
- **Markdown**: `API_DOCUMENTATION.md`

### User Guides
- **Testing**: `TESTING_GUIDE.md`
- **Implementation**: `IMPLEMENTATION_SUMMARY.md`

---

## 🐛 Troubleshooting

### Port 8080 Already in Use
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### MySQL Connection Failed
```bash
# Check MySQL is running
# Windows
net start MySQL80

# Linux
sudo systemctl start mysql

# Test connection
mysql -u root -p
```

### Tests Failing
```bash
# Clean build
mvn clean build

# Clear Maven cache
mvn clean -U install

# Check Java version (should be 21)
java -version
```

### JWT Token Expired
```bash
# Get new token by logging in again
POST /api/auth/login
```

---

## 📦 Project Structure

```
bitsa-backend/
├── src/
│   ├── main/
│   │   ├── java/com/bitsa/api/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Data access
│   │   │   ├── model/           # JPA entities
│   │   │   ├── dto/             # Data transfer objects
│   │   │   └── security/        # Security components
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/bitsa/api/
│           ├── service/         # Unit tests
│           └── controller/      # Integration tests
├── pom.xml                       # Maven configuration
├── API_DOCUMENTATION.md          # Full API reference
├── TESTING_GUIDE.md              # Testing instructions
└── IMPLEMENTATION_SUMMARY.md     # Implementation overview
```

---

## 🚀 Deployment

### Build Production JAR
```bash
mvn clean package -DskipTests
```

JAR location: `target/bitsa-backend-0.0.1-SNAPSHOT.jar`

### Run JAR
```bash
java -jar target/bitsa-backend-0.0.1-SNAPSHOT.jar
```

### Docker Build (Optional)
```bash
docker build -t bitsa-backend:latest .
docker run -p 8080:8080 bitsa-backend:latest
```

---

## 📞 Common Commands

### Maven
```bash
mvn clean              # Clean build
mvn build              # Build project
mvn test               # Run tests
mvn spring-boot:run    # Run application
mvn package            # Create JAR
```

### Git
```bash
git status             # Check status
git add .              # Stage changes
git commit -m "msg"    # Commit
git push               # Push to remote
```

---

## 🎓 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT Guide](https://tools.ietf.org/html/rfc7519)
- [React Documentation](https://react.dev)
- [Axios Documentation](https://axios-http.com)

---

## ✅ Checklist for New Developers

- [ ] Clone repository
- [ ] Install Java 21
- [ ] Install MySQL 8
- [ ] Import project to IDE
- [ ] Configure database in `application.properties`
- [ ] Run `mvn clean install`
- [ ] Run tests: `mvn test`
- [ ] Start backend: `mvn spring-boot:run`
- [ ] Access API docs: http://localhost:8080/swagger-ui.html
- [ ] Read `API_DOCUMENTATION.md`
- [ ] Read `TESTING_GUIDE.md`

---

## 📝 Notes

- All passwords are hashed with BCrypt
- Tokens expire after 1 hour
- Images are stored in `/uploads` directory
- Database queries use case-insensitive search
- Pagination is zero-based (page 0 = first page)
- All timestamps are in UTC

---

**Last Updated**: January 2025  
**Version**: 1.0.0  
**Status**: Production Ready ✅
