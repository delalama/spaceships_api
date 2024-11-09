

## Liquibase update command 
mvn liquibase:update -Dliquibase.url=jdbc:h2:mem:testdb -Dliquibase.username=sa -Dliquibase.password=password -Dliquibase.driver=org.h2.Driver -Dliquibase.changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml 


## Access to Swagger
http://localhost:8080/swagger-ui/