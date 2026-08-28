# Student Management System

A Java Spring Boot web application for managing students and course enrollment — built with a clean, layered architecture covering authentication, validation, pagination, DTO mapping, and centralized logging.

## Features

- **Authentication & Authorization** — secure login and access control with Spring Security
- **Student CRUD** — create, read, update, and delete student records
- **Course Enrollment** — enroll students into courses, with paginated and sortable course listings
- **Form Validation** — server-side validation on student and course data
- **Global Exception Handling** — centralized error handling for consistent, predictable API/UI responses
- **DTO Mapping** — clean separation between entities and view/API models using **ModelMapper**
- **AOP-Based Request Logging** — custom Spring AOP aspect around controller methods for centralized request/response logging, added independently to trace requests without cluttering business logic
- **Dashboard** — overview view for managing students and courses
- **Responsive UI** — server-rendered views with Thymeleaf

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot, Spring MVC
- **Security:** Spring Security
- **Persistence:** Spring Data JPA / Hibernate, MySQL
- **View Layer:** Thymeleaf
- **Mapping:** ModelMapper
- **Cross-Cutting Concerns:** Spring AOP
- **Build Tool:** Maven

## Getting Started

### Prerequisites

- Java 17+ (or your configured JDK version)
- Maven (or use the included `mvnw` wrapper)
- MySQL

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/williammounir/student-management-system.git
   cd student-management-system
   ```

2. Create a MySQL database and update `src/main/resources/application.properties` (or `application.yml`) with your database URL, username, and password.

3. Run the application
   ```bash
   ./mvnw spring-boot:run
   ```

4. The app will be available at `http://localhost:8080` (or your configured port).

## Project Structure

```
student-management-system/
├── src/
│   └── main/
│       ├── java/          # Controllers, services, repositories, entities, DTOs, AOP aspects
│       └── resources/     # Configuration, Thymeleaf templates, static assets
├── pom.xml
└── mvnw / mvnw.cmd
```

## Notes

Built as a hands-on project to apply core Spring Boot backend patterns end-to-end — layered architecture, security, DTO mapping, and AOP — beyond basic CRUD scaffolding.
