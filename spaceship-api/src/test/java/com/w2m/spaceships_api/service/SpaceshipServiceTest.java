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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpaceshipServiceTest {

    final String BIRELIAN = "Birelian";

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @Mock
    private SpaceshipMapper spaceshipMapper;

    @InjectMocks
    private SpaceshipService spaceshipService;

    private Spaceship spaceship;

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

        when(spaceshipMapper.toDto(any(Spaceship.class))).thenReturn(SpaceshipDTO.builder().id(1L).name(BIRELIAN).build());

        Page<SpaceshipDTO> result = spaceshipService.getAllSpaceships(0, 1);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetSpaceshipById() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceship));

        when(spaceshipMapper.toDto(spaceship)).thenReturn(SpaceshipDTO.builder().id(1L).name(BIRELIAN).build());

        Optional<SpaceshipDTO> result = spaceshipService.getSpaceshipById(1L);

        assertTrue(result.isPresent(), "Spaceship should be found");
        assertEquals(spaceship.getId(), result.get().getId(), "The spaceship ID should match");

        verify(spaceshipRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSpaceshipsByName() {
        List<Spaceship> list = Collections.singletonList(spaceship);

        when(spaceshipRepository.findByNameContainingIgnoreCase(BIRELIAN)).thenReturn(list);

        when(spaceshipMapper.toDtos(list)).thenReturn(Collections.singletonList(
                SpaceshipDTO.builder().id(1L).name(BIRELIAN).build()));

        List<SpaceshipDTO> result = spaceshipService.getSpaceshipsByName(BIRELIAN);

        assertEquals(1, result.size());
        assertEquals(BIRELIAN, result.get(0).getName());
    }


    @Test
    void testCreateSpaceship() {
        SpaceshipDTO spaceshipDTO = SpaceshipDTO.builder()
                .id(1L)
                .name(BIRELIAN)
                .model("Model X")
                .creationDate(LocalDate.now())
                .build();

        Spaceship spaceship = Spaceship.builder()
                .id(1L)
                .name(BIRELIAN)
                .model("Model X")
                .creationDate(LocalDate.now())
                .build();

        when(spaceshipMapper.toEntity(spaceshipDTO)).thenReturn(spaceship);

        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(spaceship);

        when(spaceshipMapper.toDto(spaceship)).thenReturn(spaceshipDTO);

        SpaceshipDTO result = spaceshipService.createSpaceship(spaceshipDTO);

        assertNotNull(result, "Result should not be null");
        assertEquals(spaceship.getId(), result.getId());
        assertEquals(spaceship.getName(), result.getName());
        assertEquals(spaceship.getModel(), result.getModel());
        assertEquals(spaceship.getCreationDate(), result.getCreationDate());
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
                .id(1L)
                .name("Spaceship Updated")
                .model("Model Updated")
                .creationDate(LocalDate.of(2024, 2, 1))
                .build();

        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(existingSpaceship));
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(existingSpaceship);

        when(spaceshipMapper.toDto(any(Spaceship.class))).thenReturn(updatedSpaceshipDTO);

        SpaceshipDTO result = spaceshipService.updateSpaceship(1L, updatedSpaceshipDTO);

        assertEquals(result.getId(), existingSpaceship.getId());
        assertEquals("Spaceship Updated", result.getName());
        assertEquals("Model Updated", result.getModel());
        assertEquals(LocalDate.of(2024, 2, 1), result.getCreationDate());
    }
}
