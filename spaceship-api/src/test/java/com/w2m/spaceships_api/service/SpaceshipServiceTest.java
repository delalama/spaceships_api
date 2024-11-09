package com.w2m.spaceships_api.service;

import com.w2m.spaceships_api.exception.SpaceshipApiException;
import com.w2m.spaceships_api.model.Spaceship;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

    String BIRELIAN = "Birelian";

    @BeforeEach
    void setUp() {
        spaceship = new Spaceship();
        spaceship.setId(1L);
        spaceship.setName(BIRELIAN);
    }

    @Test
    void testGetAllSpaceships() {
        Page<Spaceship> page = new PageImpl<>(Arrays.asList(spaceship));
        when(spaceshipRepository.findAll(any(PageRequest.class))).thenReturn(page);
        Page<Spaceship> result = spaceshipService.getAllSpaceships(0, 1);
        assertEquals(1, result.getTotalElements());
        verify(spaceshipRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetSpaceshipById() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceship));
        Spaceship result = spaceshipService.getSpaceshipById(1L);
        assertEquals(spaceship.getId(), result.getId());
        verify(spaceshipRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSpaceshipById_NotFound() {
        when(spaceshipRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(SpaceshipApiException.class, () -> spaceshipService.getSpaceshipById(1L));
        verify(spaceshipRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSpaceshipsByName() {
        List<Spaceship> list = Arrays.asList(spaceship);
        when(spaceshipRepository.findByNameContaining(BIRELIAN)).thenReturn(list);
        List<Spaceship> result = spaceshipService.getSpaceshipsByName(BIRELIAN);
        assertEquals(1, result.size());
        verify(spaceshipRepository, times(1)).findByNameContaining(BIRELIAN);
    }

    @Test
    void testCreateSpaceship() {
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(spaceship);
        Spaceship result = spaceshipService.createSpaceship(spaceship);
        assertEquals(spaceship.getId(), result.getId());
        verify(spaceshipRepository, times(1)).save(spaceship);
    }

    @Test
    void testUpdateSpaceship() {
        when(spaceshipRepository.save(any(Spaceship.class))).thenReturn(spaceship);
        Spaceship result = spaceshipService.updateSpaceship(1L, spaceship);
        assertEquals(spaceship.getId(), result.getId());
        verify(spaceshipRepository, times(1)).save(spaceship);
    }

    @Test
    void testDeleteSpaceship() {
        doNothing().when(spaceshipRepository).deleteById(1L);
        spaceshipService.deleteSpaceship(1L);
        verify(spaceshipRepository, times(1)).deleteById(1L);
    }
}