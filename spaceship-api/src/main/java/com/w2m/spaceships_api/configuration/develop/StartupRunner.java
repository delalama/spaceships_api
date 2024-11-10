package com.w2m.spaceships_api.configuration.develop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("develop")
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("SWAGGER LINK: http://localhost:8080/swagger-ui/index.html");
        log.info("H2 LINK: http://localhost:8080/h2-console");
    }
}