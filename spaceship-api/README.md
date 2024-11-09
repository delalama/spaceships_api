# Spaceship API Project

This is a Java project using Spring Boot with Liquibase for database version control and Swagger for API documentation.

## Prerequisites
- Java 21
- Maven

## Basic Commands

### 1. Compile, Download Dependencies, and Run Tests
This command cleans the previous build, installs dependencies, and runs the tests.

```bash
mvn clean install
``` 

### 2. Start the Application
This command starts the Spring Boot application on port 8080.
```bash
mvn spring-boot:run
``` 

### 3. Access Swagger
Once the application is running, you can access the API documentation in Swagger:
```bash
http://localhost:8080/swagger-ui.html
```


### 4. Docker
To build and run the Docker container for the application, use the following commands:
```bash
docker build -t spaceships-api .
docker run -p 8080:8080 spaceships-api
docker-compose up
``` 
If you encounter Docker errors, execute the following commands and repeat the build process:

```bash
docker builder prune -a
docker build --no-cache -t spaceships-api .
``` 

### 5. Profiles
To build and run the Docker container for the application, use the following commands:
```bash
docker build -t spaceships-api .
docker run -p 8080:8080 spaceships-api
docker-compose up
``` 
develop: All endpoints are open (no authentication).

prod: Only admin should be able to consume endpoints(basic user/pass authentication required).

