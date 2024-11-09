package com.w2m.spaceships_api.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SwaggerConfiguration implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(final ApplicationPreparedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        Properties props = new Properties();
        props.put("springdoc.swagger-ui.path", swaggerPath());
        environment.getPropertySources()
                .addFirst(new PropertiesPropertySource("programmatically", props));
    }

    private String swaggerPath() {
        return "/swagger-ui.html";
    }
}