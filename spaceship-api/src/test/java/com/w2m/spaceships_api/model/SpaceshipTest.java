package com.w2m.spaceships_api.model;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpaceshipTest {

    private String BIRELIAN = "BIRELIAN";
    @Test
    public void testSpaceshipGettersAndSetters() {
        Spaceship spaceship = new Spaceship();
        Date creationDate = new Date();
        spaceship.setId(1L);
        spaceship.setName(BIRELIAN);
        spaceship.setModel("X-100");
        spaceship.setCreationDate(creationDate);
        assertEquals(1L, spaceship.getId());
        assertEquals(BIRELIAN, spaceship.getName());
        assertEquals("X-100", spaceship.getModel());
        assertEquals(creationDate, spaceship.getCreationDate());
    }

    @Test
    public void testSpaceshipBuilder() {
        Date creationDate = new Date();
        Spaceship spaceship = Spaceship.builder().id(1L).name(BIRELIAN).model("X-100").creationDate(creationDate).build();
        assertEquals(1L, spaceship.getId());
        assertEquals(BIRELIAN, spaceship.getName());
        assertEquals("X-100", spaceship.getModel());
        assertEquals(creationDate, spaceship.getCreationDate());
    }

    @Test
    public void testSpaceshipAllArgsConstructor() {
        Date creationDate = new Date();
        Spaceship spaceship = new Spaceship(1L, BIRELIAN, "X-100", creationDate);
        assertEquals(1L, spaceship.getId());
        assertEquals(BIRELIAN, spaceship.getName());
        assertEquals("X-100", spaceship.getModel());
        assertEquals(creationDate, spaceship.getCreationDate());
    }

    @Test
    public void testSpaceshipNoArgsConstructor() {
        Spaceship spaceship = new Spaceship();
        assertNotNull(spaceship);
        assertNull(spaceship.getId());
        assertNull(spaceship.getName());
        assertNull(spaceship.getModel());
        assertNull(spaceship.getCreationDate());
    }
}