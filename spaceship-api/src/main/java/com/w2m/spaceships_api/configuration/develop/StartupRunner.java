package com.w2m.spaceships_api.configuration.develop;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("develop")
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("SWAGGER LINK: http://localhost:8080/swagger-ui/index.html");
        System.out.println("H2 LINK: http://localhost:8080/h2-console");
    }
}