package com.w2m.spaceships_api.service;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.repository.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpaceshipServiceCacheTest {

    @Autowired
    private SpaceshipService spaceshipService;

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @Test
    void testCacheSaveRepositoryAccesses() {
        Long spaceshipId = 1L;
        Spaceship spaceship = Spaceship.builder()
                .id(spaceshipId)
                .name("Galactica")
                .model("X-100")
                .creationDate(LocalDate.of(2024, 1, 1))
                .build();

        when(spaceshipRepository.findById(spaceshipId)).thenReturn(Optional.of(spaceship));

        spaceshipService.getSpaceshipById(spaceshipId);
        verify(spaceshipRepository, times(1)).findById(spaceshipId);

        spaceshipService.getSpaceshipById(spaceshipId);
        verify(spaceshipRepository, times(1)).findById(spaceshipId);
    }


}