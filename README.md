![CI](https://github.com/williammounir/student-management-system/actions/workflows/ci.yml/badge.svg)

# Student Management System

A Java Spring Boot web application for managing students and course enrollment, built with a clean, layered architecture covering authentication, validation, pagination, DTO mapping, and centralized logging. It is containerized with Docker and tested automatically with GitHub Actions.

## Features

- **Authentication & Authorization**: secure login and access control with Spring Security
- **Student CRUD**: create, read, update, and delete student records
- **Course Enrollment**: enroll students into courses, with paginated and sortable course listings
- **Form Validation**: server-side validation on student and course data
- **Global Exception Handling**: centralized error handling for consistent, predictable API/UI responses
- **DTO Mapping**: clean separation between entities and view/API models using **ModelMapper**
- **AOP-Based Request Logging**: custom Spring AOP aspect around controller methods for centralized request/response logging, added independently to trace requests without cluttering business logic
- **Dashboard**: overview view for managing students and courses
- **Responsive UI**: server-rendered views with Thymeleaf
- **Docker Support**: one-command setup with Docker Compose (app + MySQL)
- **CI Pipeline**: GitHub Actions builds the project, runs tests against a MySQL service, and builds the Docker image on every push

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot, Spring MVC
- **Security:** Spring Security
- **Persistence:** Spring Data JPA / Hibernate, MySQL
- **View Layer:** Thymeleaf
- **Mapping:** ModelMapper
- **Cross-Cutting Concerns:** Spring AOP
- **Build Tool:** Maven
- **DevOps:** Docker, Docker Compose, GitHub Actions

## Getting Started

### Option 1: Run with Docker (recommended)

**Requirements:** Docker and Docker Compose.

1. Clone the repository
   ```bash
   git clone https://github.com/williammounir/student-management-system.git
   cd student-management-system
   ```

2. Build and start the app and its MySQL database
   ```bash
   docker compose up --build
   ```

3. Open the app at `http://localhost:7070`

To stop it:

```bash
docker compose down        # stop containers, keep database data
docker compose down -v     # stop containers and delete database data
```

To run it in the background, use `docker compose up -d`.

### Option 2: Run locally

**Prerequisites:** Java 17+, Maven (or the included `mvnw` wrapper), and a running MySQL server.

1. Clone the repository
   ```bash
   git clone https://github.com/williammounir/student-management-system.git
   cd student-management-system
   ```

2. Update `src/main/resources/application.properties` with your MySQL URL, username, and password.

3. Run the application
   ```bash
   ./mvnw spring-boot:run
   ```

4. The app will be available at `http://localhost:7070`.

## Docker Setup

The project includes:

| File | Purpose |
|---|---|
| `Dockerfile` | Multi-stage build: compiles the app with Maven, then runs the jar on a slim Java 17 runtime image |
| `docker-compose.yml` | Starts the app and a MySQL 8.4 database together, with a health check so the app waits for the database |
| `.dockerignore` | Keeps build output and IDE files out of the image |

### Configuration

Inside Docker, the app is configured with environment variables set in `docker-compose.yml`. Spring Boot lets these override `application.properties`, so no code changes are needed.

| Variable | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` | JDBC URL (the database host is the `mysql` service name) |
| `SPRING_DATASOURCE_USERNAME` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Schema update mode |

The credentials in `docker-compose.yml` are for local development only. Change them for any real deployment.

## CI Pipeline

The workflow in `.github/workflows/ci.yml` runs on every push and pull request:

1. Starts a MySQL 8.4 service container for the tests
2. Sets up Java 17 with Maven dependency caching
3. Builds the project and runs the tests with `mvn verify`
4. Builds the Docker image to confirm the Dockerfile works

## Project Structure

```
student-management-system/
├── src/
│   └── main/
│       ├── java/          # Controllers, services, repositories, entities, DTOs, AOP aspects
│       └── resources/     # Configuration, Thymeleaf templates, static assets
├── .github/
│   └── workflows/
│       └── ci.yml         # GitHub Actions pipeline
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── pom.xml
└── mvnw / mvnw.cmd
```

## Notes

Built as a hands-on project to apply core Spring Boot backend patterns end-to-end (layered architecture, security, DTO mapping, and AOP) beyond basic CRUD scaffolding, then extended with containerization and CI/CD.
