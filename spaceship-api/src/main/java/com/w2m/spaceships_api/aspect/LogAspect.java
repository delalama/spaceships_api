package com.w2m.spaceships_api.aspect;

import com.w2m.spaceships_api.utils.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.w2m.spaceships_api.utils.Logger.Level.INFO;

@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.w2m.spaceships_api.service.SpaceshipService.getSpaceshipById(Long)) && args(id)")
    public void logBeforeGetSpaceshipById(Long id) {
        if (id < 0) {
            Logger.log("ASPECT : Trying to fetch a spaceship with a negative ID: " + id, INFO);
        }
    }

}
