# BITSA Backend - Testing & Documentation Guide

## Overview

This document provides comprehensive instructions for running tests and accessing API documentation for the BITSA Backend API.

---

## Testing

### Running All Tests

#### Using Maven
```bash
cd bitsa-backend
mvn test
```

#### Using Maven with Coverage
```bash
mvn test jacoco:report
```

Coverage report will be generated at: `target/site/jacoco/index.html`

---

### Running Specific Test Suites

#### Unit Tests Only (Service Layer)
```bash
mvn test -Dtest=*ServiceTests
```

Tests included:
- `BlogServiceTests` - Blog service business logic
- `EventServiceTests` - Event service business logic
- `CommentServiceTests` - Comment management
- `RatingServiceTests` - Blog rating system
- `EventRegistrationServiceTests` - Event registration logic

#### Integration Tests Only (Controller Layer)
```bash
mvn test -Dtest=*IntegrationTests
```

Tests included:
- `BlogControllerIntegrationTests` - Blog API endpoints
- `EventControllerIntegrationTests` - Event API endpoints

#### Individual Test Class
```bash
mvn test -Dtest=BlogServiceTests
mvn test -Dtest=EventControllerIntegrationTests
```

---

### Running Tests in IDE

#### IntelliJ IDEA
1. Right-click on test class → "Run Tests"
2. Or use keyboard shortcut: `Ctrl+Shift+F10` (Windows/Linux) or `Cmd+Shift+R` (Mac)
3. For entire test suite: Right-click on `src/test/java` folder

#### Eclipse
1. Right-click on test class → "Run As" → "JUnit Test"
2. Use keyboard shortcut: `Alt+Shift+X` then `T`

#### VS Code
1. Install "Test Runner" extension
2. Click on test class codelens "Run Test" link

---

### Test Coverage

Current test coverage includes:

#### Service Layer (Unit Tests)
- **BlogServiceTests** (7 tests)
  - Create, read, update, delete operations
  - Search with pagination
  - Find by author functionality

- **EventServiceTests** (7 tests)
  - Event CRUD operations
  - Search functionality
  - Event creator queries

- **CommentServiceTests** (7 tests)
  - Add, update, delete comments
  - Get comments by blog
  - Comment retrieval

- **RatingServiceTests** (7 tests)
  - Add/update rating (upsert)
  - Get ratings by blog
  - Average rating calculation
  - Delete rating

- **EventRegistrationServiceTests** (8 tests)
  - Register for event
  - Update registration status
  - Cancel registration
  - Get registrations (event and user)
  - Count registrations

#### Controller Layer (Integration Tests)
- **BlogControllerIntegrationTests** (9 tests)
  - Authentication and authorization checks
  - CRUD operations via HTTP
  - Search and pagination
  - Role-based access control

- **EventControllerIntegrationTests** (9 tests)
  - Event creation with role checks
  - Event deletion authorization
  - Search functionality
  - Role hierarchy validation

**Total: 54 comprehensive tests covering core functionality**

---

## API Documentation

### Interactive Swagger UI

Access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

Features:
- Try out API endpoints directly from the browser
- View complete request/response schemas
- Automatic JWT token management
- Real-time API testing

### OpenAPI JSON Specification

OpenAPI specification available at:

```
http://localhost:8080/v3/api-docs
```

This is the raw OpenAPI 3.0 specification that can be imported into:
- Postman
- Insomnia
- Other API clients supporting OpenAPI

---

### Markdown Documentation

Comprehensive API documentation is available in:

```
API_DOCUMENTATION.md
```

Includes:
- All endpoint specifications with request/response examples
- Authentication and authorization details
- Error handling guide
- Rate limiting information
- Getting started guide with curl examples
- Role-based access control matrix

---

## Importing API to Postman

### Method 1: Via OpenAPI URL

1. Open Postman
2. Click "File" → "Import"
3. Go to "Link" tab
4. Enter: `http://localhost:8080/v3/api-docs`
5. Click "Continue" then "Import"

### Method 2: Via Swagger UI

1. In Swagger UI (http://localhost:8080/swagger-ui.html)
2. Click the "download" icon in top-right corner
3. Open the downloaded JSON in Postman via "Import"

---

## Authentication in Postman

### Setting Up Bearer Token

1. Register a new user via `POST /auth/register`
2. Copy the returned `token` value
3. In Postman, go to "Collections" or individual requests
4. Go to "Authorization" tab
5. Select type: "Bearer Token"
6. Paste token in the token field

### Postman Pre-request Script

Automatically get and use token:

```javascript
// Get login token (set username/password first)
const loginRequest = {
    url: pm.environment.get("base_url") + "/auth/login",
    method: 'POST',
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            email: pm.environment.get("user_email"),
            password: pm.environment.get("user_password")
        })
    }
};

pm.sendRequest(loginRequest, (err, response) => {
    if (!err) {
        const token = response.json().token;
        pm.environment.set("bearer_token", token);
    }
});
```

---

## Building and Running

### Build Application
```bash
mvn clean build
```

### Run Application
```bash
mvn spring-boot:run
```

### Run Application with Profile
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

---

## Test Database Setup

Tests use an in-memory H2 database. Configuration:

**File:** `src/test/resources/application-test.properties`

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
```

Database is automatically:
- Created before tests
- Populated with test data
- Dropped after tests complete

---

## Debugging Tests

### Enable Debug Logging

Edit `src/test/resources/logback-test.xml`:

```xml
<logger name="com.bitsa.api" level="DEBUG"/>
<logger name="org.springframework.security" level="DEBUG"/>
```

### Run Tests in Debug Mode

```bash
mvn test -Dmaven.surefire.debug
```

Then attach debugger to port 5005 in IDE.

---

## CI/CD Integration

### GitHub Actions Example

```yaml
name: Run Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '21'
      - run: mvn clean test
      - run: mvn jacoco:report
      - uses: codecov/codecov-action@v2
```

---

## Known Test Limitations

1. **File Upload Tests**: Tests for `FileStorageService` require mock implementations
2. **JWT Validation**: Token expiration tested via mocking
3. **Database Constraints**: Some unique constraints tested via exception handling
4. **Email Verification**: Not tested (would require email mock)

---

## Extending Tests

### Adding New Test Cases

#### Unit Test Template
```java
@ExtendWith(MockitoExtension.class)
class NewServiceTests {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private ServiceImpl service;
    
    @Test
    void testNewFunctionality() {
        // Given
        when(repository.method()).thenReturn(value);
        
        // When
        Result result = service.method();
        
        // Then
        assertEquals(expected, result);
        verify(repository, times(1)).method();
    }
}
```

#### Integration Test Template
```java
@SpringBootTest
@AutoConfigureMockMvc
class NewControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testEndpoint() throws Exception {
        mockMvc.perform(get("/api/endpoint"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.field").exists());
    }
}
```

---

## Performance Benchmarks

Expected test execution times:

- **Unit Tests**: ~2-3 seconds (30 tests)
- **Integration Tests**: ~4-5 seconds (24 tests)
- **Total Test Suite**: ~6-8 seconds (54 tests)
- **With Coverage**: ~15-20 seconds

---

## Troubleshooting

### Tests Failing on CI but Passing Locally

**Solution**: Check Java version
```bash
java -version  # Should be 21
mvn --version  # Should support Java 21
```

### Port Already in Use

```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9  # Linux/Mac
netstat -ano | findstr :8080   # Windows
```

### Database Connection Issues

Ensure MySQL is running:
```bash
# Start MySQL
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8
```

### JWT Token Expired in Postman

Re-run login request to get new token:
```bash
POST /auth/login
```

---

## Additional Resources

- **Spring Boot Testing Guide**: https://spring.io/guides/gs/testing-web/
- **Mockito Documentation**: https://javadoc.io/doc/org.mockito/mockito-core
- **Spring Security Testing**: https://docs.spring.io/spring-security/reference/testing.html
- **OpenAPI Specification**: https://swagger.io/specification/

---

## Questions or Issues?

For issues with tests or API documentation:
1. Check API_DOCUMENTATION.md for endpoint specifications
2. Review test class comments for expected behavior
3. Check logs in `target/test-logs/`
4. Consult Spring Boot testing documentation

Contact: support@bitsa.com
