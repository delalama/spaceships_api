package com.w2m.spaceships_api.aspect;

import com.w2m.spaceships_api.exception.SpaceshipApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("execution(* com.w2m.spaceships_api.service.SpaceshipService.getSpaceshipById(Long)) && args(id)")
    public void logBeforeGetSpaceshipById(Long id) {
        if (id < 0) {
            throw new SpaceshipApiException("ASPECT : Trying to fetch a spaceship with a negative ID: " + id, Level.INFO);
        }
    }

}
