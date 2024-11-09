package com.w2m.spaceships_api.configuration.prod;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.w2m.spaceships_api.configuration.ConfigConstants.SWAGGER;
import static com.w2m.spaceships_api.configuration.ConfigConstants.V3_API_DOCS;
import static com.w2m.spaceships_api.configuration.ConfigConstants.H2_CONSOLE;


@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityConfigProd {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("w2m_admin")
                        .password("{noop}w2m_admin")
                        .roles("ADMIN")
                        .build(),
                User.withUsername("w2m_user")
                        .password("{noop}w2m_user")
                        .roles("USER")
                        .build()
        );
    }
}