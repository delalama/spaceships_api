package com.w2m.spaceships_api.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpaceshipTest {

    private final String BIRELIAN = "BIRELIAN";

    @Test
    public void testSpaceshipGettersAndSetters() {
        Spaceship spaceship = new Spaceship();
        LocalDate creationDate = LocalDate.now();
        spaceship.setId(1L);
        spaceship.setName(BIRELIAN);
        spaceship.setModel("X-100");
        spaceship.setCreationDate(LocalDate.now());
        assertEquals(1L, spaceship.getId());
        assertEquals(BIRELIAN, spaceship.getName());
        assertEquals("X-100", spaceship.getModel());
        assertEquals(creationDate, spaceship.getCreationDate());
    }

    @Test
    public void testSpaceshipBuilder() {
        LocalDate simpleDate = LocalDate.of(2024, 1, 1);
        Spaceship spaceship = Spaceship.builder().id(1L).name(BIRELIAN).model("X-100").creationDate(simpleDate).build();
        assertEquals(1L, spaceship.getId());
        assertEquals(BIRELIAN, spaceship.getName());
        assertEquals("X-100", spaceship.getModel());
        assertEquals(simpleDate, spaceship.getCreationDate());
    }

    @Test
    public void testSpaceshipAllArgsConstructor() {
        LocalDate simpleDate = LocalDate.of(2024, 1, 1);
        Spaceship spaceship = new Spaceship(1L, BIRELIAN, "X-100", simpleDate);
        assertEquals(1L, spaceship.getId());
        assertEquals(BIRELIAN, spaceship.getName());
        assertEquals("X-100", spaceship.getModel());
        assertEquals(simpleDate, spaceship.getCreationDate());
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

    @Test
    public void testSpaceshipSerializer() {
        Spaceship spaceship = Spaceship.builder()
                .id(1L)
                .name(BIRELIAN)
                .creationDate(LocalDate.of(2024, 1, 1))
                .build();

        String json = spaceship.toJson();
    }
}