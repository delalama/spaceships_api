package com.w2m.spaceships_api.configuration.develop;

import com.w2m.spaceships_api.utils.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.w2m.spaceships_api.utils.Logger.Level.CUSTOM;

@Component
@Profile("develop")
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        Logger.log("SWAGGER LINK: http://localhost:8080/swagger-ui/index.html", CUSTOM);
        Logger.log("H2 LINK: http://localhost:8080/h2-console", CUSTOM);
    }
}