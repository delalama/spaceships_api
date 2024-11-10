package com.w2m.spaceships_api.service;

import com.w2m.spaceships_api.mapper.SpaceshipMapper;
import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.model.SpaceshipDTO;
import com.w2m.spaceships_api.repository.SpaceshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpaceshipServiceTest {

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipService spaceshipService;

    private Spaceship spaceship;

    final String BIRELIAN = "Birelian";

    @BeforeEach
    void setUp() {
        spaceship = new Spaceship();
        spaceship.setId(1L);
        spaceship.setName(BIRELIAN);
    }

    @Test
    void testGetAllSpaceships() {
        Page<Spaceship> page = new PageImpl<>(Collections.singletonList(spaceship));
        when(spaceshipRepository.findAll(any(PageRequest.class))).thenReturn(page);
        Page<SpaceshipDTO> result = spaceshipService.getAllSpaceships(0, 1);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetSpaceshipById() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceship));

        Optional<SpaceshipDTO> result = spaceshipService.getSpaceshipById(1L);

        if (result.isPresent()) {
            assertEquals(spaceship.getId(), result.get().getId());
        } else {
            fail("Spaceship not found");
        }

        verify(spaceshipRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSpaceshipsByName() {
        List<Spaceship> list = Collections.singletonList(spaceship);
        when(spaceshipRepository.findByNameContainingIgnoreCase(BIRELIAN)).thenReturn(list);
        List<SpaceshipDTO> result = spaceshipService.getSpaceshipsByName(BIRELIAN);
        assertEquals(result.get(0).getName(), BIRELIAN);
    }

    @Test
    void testCreateSpaceship() {
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(spaceship);
        SpaceshipDTO result = spaceshipService.createSpaceship(SpaceshipMapper.toDTO(spaceship));
        assertEquals(spaceship.getId(), result.getId());
    }

    @Test
    void testUpdateSpaceship() {
        Spaceship existingSpaceship = Spaceship.builder()
                .id(1L)
                .name("Spaceship1")
                .model("Model1")
                .creationDate(LocalDate.of(2024, 1, 1))
                .build();

        SpaceshipDTO updatedSpaceshipDTO = SpaceshipDTO.builder()
                .name("Spaceship Updated")
                .model("Model Updated")
                .creationDate(LocalDate.of(2024, 2, 1))
                .build();

        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(existingSpaceship));
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(existingSpaceship);

        SpaceshipDTO result = spaceshipService.updateSpaceship(1L, updatedSpaceshipDTO);

        assertEquals(result.getId(), existingSpaceship.getId());
        assertEquals("Spaceship Updated", result.getName());
        assertEquals("Model Updated", result.getModel());
        assertEquals(LocalDate.of(2024, 2, 1), result.getCreationDate());
    }

}