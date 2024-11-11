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

### 2 options
For evaluation ease first option has develop profile, second prod.
#### - Maven

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```

#### - Docker
Select profile by switching entrypoints on dockerfile
```bash
docker-compose up --build
```


## Access to Swagger

Once the application is running, you can access the API documentation , on prod profile you need to authenticate

```bash
http://localhost:8080/swagger-ui.html
```

## Profiles

develop: All endpoints are open (no authentication).

prod: Only admin should be able to consume endpoints(basic user/pass authentication required).

You can switch between them on application.properties

## Evaluation
The implementation of the queueing system is not being carried out due to configuration problems, the kafka branch has a commit with the implementation.

I have no past knowledge of implementing the queuing system but I understood that the manufacturing of the spaceships could become an asynchronous system, which would persist a ship with an IN_PRODUCTION state, and then send a message to the ship factory, which would be responsible for the manufacturing, when the factory finishes building the ship it sends another kafka message, which is collected in the SpaceshipService , which is responsible for persisting the final state, which can be CREATED, COMPLETED or FAILED

This way the service does not have to wait for an asynchronous response and our API has more availability.

![Kafka_diagram](https://github.com/delalama/spaceships_api/blob/master/spaceship-api/kafka%20on%20spaceship%20API.png)