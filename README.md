# BITSA - BITSA Academic System

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.2.0-blue)](https://reactjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)

A comprehensive academic management platform designed for Department of information of information system and computing, enabling seamless interaction between students, lecturers, and administrators.

## 🌟 Features

### 👥 User Management
- **Role-based Access Control**: Admin, Lecturer, and Student roles
- **Secure Authentication**: JWT-based authentication system
- **Profile Management**: User profiles with avatar support

### 📅 Event Management
- **Event Creation**: Lecturers and admins can create events
- **Event Registration**: Students can register for events
- **Registration Tracking**: Monitor attendance and registration status
- **Event Categories**: Organized event listings

### 📝 Blog System
- **Content Creation**: Lecturers and admins can publish blogs
- **Rich Content**: Support for multimedia content
- **Comment System**: Interactive discussions on blog posts
- **Rating System**: User feedback and ratings

### 🖼️ Gallery Management
- **Image Upload**: Secure file upload system
- **Gallery Display**: Organized photo galleries
- **File Management**: Admin controls for content moderation

### 📊 Analytics & Statistics
- **Dashboard Analytics**: Comprehensive admin dashboard
- **User Statistics**: Track user engagement
- **Event Analytics**: Monitor event participation
- **System Metrics**: Performance and usage statistics

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.5.7
- **Language**: Java 21
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT
- **Documentation**: OpenAPI/Swagger
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18.2.0
- **Build Tool**: Vite
- **Styling**: CSS Modules
- **State Management**: React Context
- **Routing**: React Router

### DevOps & Deployment
- **Containerization**: Docker
- **CI/CD**: GitHub Actions (optional)
- **Hosting**: Railway (recommended)
- **Database**: Railway MySQL

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Node.js 18+ and npm
- MySQL 8.0
- Maven 3.6+

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd bitsa-hackaton
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE bitsadb;
   -- Update credentials in application.properties
   ```

3. **Backend Configuration**
   ```bash
   cd bitsa-backend
   # Update src/main/resources/application.properties with your database credentials
   ```

4. **Run Backend**
   ```bash
   mvn spring-boot:run
   ```
   Backend will be available at `http://localhost:8081`

### Frontend Setup

1. **Install Dependencies**
   ```bash
   cd bitsa
   npm install
   ```

2. **Environment Configuration**
   Create `.env` file:
   ```env
   VITE_API_BASE_URL=http://localhost:8081/api
   ```

3. **Run Frontend**
   ```bash
   npm run dev
   ```
   Frontend will be available at `http://localhost:5173`

## 📁 Project Structure

```
bitsa-hackaton/
├── bitsa/                          # React Frontend
│   ├── public/
│   ├── src/
│   │   ├── components/            # Reusable UI components
│   │   ├── pages/                 # Page components
│   │   ├── context/               # React context providers
│   │   ├── styles/                # CSS stylesheets
│   │   └── data/                  # Static data files
│   ├── package.json
│   └── vite.config.js
├── bitsa-backend/                  # Spring Boot Backend
│   ├── src/main/java/com/bitsa/api/
│   │   ├── controller/            # REST controllers
│   │   ├── service/               # Business logic
│   │   ├── repository/            # Data access layer
│   │   ├── model/                 # JPA entities
│   │   ├── dto/                   # Data transfer objects
│   │   ├── security/              # Security configuration
│   │   └── config/                # Application configuration
│   ├── src/main/resources/
│   │   └── application.properties # Application configuration
│   └── pom.xml
├── README.md                       # Project documentation
├── Documentation.md                # Detailed documentation
└── TODO.md                         # Development tasks
```

## 🔐 Authentication & Authorization

### User Roles
- **ADMIN**: Full system access, user management, content moderation
- **LECTURER**: Event and blog creation, student management
- **STUDENT**: Event registration, content consumption, commenting

### API Security
- JWT tokens for session management
- Role-based endpoint protection
- CORS configuration for cross-origin requests
- Password encryption using BCrypt

## 📊 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/profile` - Get user profile

### Events
- `GET /api/events` - List all events
- `POST /api/events` - Create event (Lecturer/Admin)
- `PUT /api/events/{id}` - Update event
- `DELETE /api/events/{id}` - Delete event

### Event Registration
- `POST /api/events/{id}/register` - Register for event
- `GET /api/events/{id}/registrations` - Get event registrations

### Blogs
- `GET /api/blogs` - List all blogs
- `POST /api/blogs` - Create blog (Lecturer/Admin)
- `GET /api/blogs/{id}` - Get blog details

### Gallery
- `GET /api/gallery` - List gallery items
- `POST /api/gallery` - Upload image (Lecturer/Admin)

For complete API documentation, see [Documentation.md](Documentation.md)

## 🚀 Deployment

### Railway (Recommended)
1. Create Railway account
2. Connect GitHub repository
3. Configure environment variables
4. Deploy automatically

### Manual Deployment
```bash
# Backend
cd bitsa-backend
mvn clean package
java -jar target/bitsa-backend-0.0.1-SNAPSHOT.jar

# Frontend
cd bitsa
npm run build
# Serve build directory with any static server
```

## 🧪 Testing

### Backend Tests
```bash
cd bitsa-backend
mvn test
```

### Frontend Tests
```bash
cd bitsa
npm test
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java/Spring Boot best practices
- Use meaningful commit messages
- Write tests for new features
- Update documentation as needed

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- **Project Lead**: [Your Name]
- **Backend Developer**: [Backend Developer]
- **Frontend Developer**: [Frontend Developer]
- **UI/UX Designer**: [Designer]

## 📞 Support

For support, email support@bitsa.edu or create an issue in this repository.

## 🔄 Version History

### v1.0.0 (Current)
- Initial release
- Core features: User management, events, blogs, gallery
- Role-based access control
- JWT authentication
- File upload system

---

**BITSA** - Empowering Academic Excellence at BITS Institute 🎓
