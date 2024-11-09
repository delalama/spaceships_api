package com.w2m.spaceships_api.aspect;

import com.w2m.spaceships_api.exception.SpaceshipApiException;
import com.w2m.spaceships_api.model.Spaceship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LogAspectTest {

    @InjectMocks
    private LogAspect logAspect;

    @BeforeEach
    public void setUp() {
        logAspect = new LogAspect();
    }

    @Test
    public void testLogBeforeGetSpaceshipById_throwsExceptionForNegativeId() {
        Long negativeId = -1L;
        assertThrows(SpaceshipApiException.class, () -> logAspect.logBeforeGetSpaceshipById(negativeId));
    }
    @Test
    public void testCeci() {
        Spaceship spaceship = new Spaceship();
        spaceship.setId(55L);
        String s = "BIRELIAN";
        spaceship.setName(s);
        spaceship.setCreationDate(new Date());
    }
}
