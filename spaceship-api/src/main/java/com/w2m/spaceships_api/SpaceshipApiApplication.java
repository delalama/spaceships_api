package com.w2m.spaceships_api;

import com.w2m.spaceships_api.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpaceshipApiApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpaceshipApiApplication.class);
		application.addListeners(new SwaggerConfiguration());
		application.run(args);
	}

}
