# Spaceship API Project

The Spaceship API is a Java project built with Spring Boot for the backend, leveraging Liquibase for database version control, and Swagger for API documentation. The
project provides endpoints for spaceship management, with configurable environments for development and production, including basic authentication.

## Dependencies

The project utilizes the following key dependencies:

- Spring Boot: The core framework for building the RESTful application.
- Liquibase: For managing database migrations and version control.
- Swagger/OpenAPI: For automatic generation of interactive API documentation.
- Spring Security: To implement basic authentication in the production environment.
- H2 Database: An in-memory database for testing purposes.
- Gson: For JSON manipulation and conversion.
- Lombok: For reducing boilerplate code by generating getters, setters, and constructors at compile-time.

## Prerequisites

Before running the project, ensure that you have the following installed:

- Java 21: The project is built with Java 21.
- Maven: For managing dependencies and building the project.
- Docker (optional): For running the application in a containerized environment.

## Launch the project

2 options

### - Maven

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```

### - Docker

```bash
docker build -t spaceships-api .
```

```bash
docker run -p 8080:8080 spaceships-api
```

```bash
docker-compose up
```

## Access to Swagger

Once the application is running, you can access the API documentation , on prod profile you need to authenticate

```bash
http://localhost:8080/swagger-ui.html
```

## Profiles

develop: All endpoints are open (no authentication).

prod: Only admin should be able to consume endpoints(basic user/pass authentication required).

You can switch on application.properties
