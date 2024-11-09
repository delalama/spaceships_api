package com.w2m.spaceships_api.configuration.develop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.w2m.spaceships_api.configuration.ConfigConstants.H2_CONSOLE;
import static com.w2m.spaceships_api.configuration.ConfigConstants.SWAGGER;
import static com.w2m.spaceships_api.configuration.ConfigConstants.V3_API_DOCS;

@Configuration
@EnableWebSecurity
@Profile("develop")
public class SecurityConfigDevelop {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth
                        .requestMatchers(SWAGGER, V3_API_DOCS, H2_CONSOLE).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                );

        return http.build();
    }
}