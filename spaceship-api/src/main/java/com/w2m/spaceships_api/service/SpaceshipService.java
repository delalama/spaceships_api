package com.w2m.spaceships_api.service;

import com.w2m.spaceships_api.exception.service.SpaceshipServiceNotFoundException;
import com.w2m.spaceships_api.mapper.SpaceshipMapper;
import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.model.SpaceshipDTO;
import com.w2m.spaceships_api.repository.SpaceshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    public Page<SpaceshipDTO> getAllSpaceships(int page, int size) {
        Page<Spaceship> spaceships = spaceshipRepository.findAll(PageRequest.of(page, size));
        return spaceships.map(SpaceshipMapper::toDTO);
    }

    @Cacheable(value = "spaceships", key = "#id")
    public Optional<SpaceshipDTO> getSpaceshipById(Long id) {
        Optional<Spaceship> spaceship = spaceshipRepository.findById(id);
        return spaceship.map(SpaceshipMapper::toDTO);
    }

    @Cacheable(value = "spaceships", key = "#namePart")
    public List<SpaceshipDTO> getSpaceshipsByName(String namePart) {
        List<Spaceship> spaceships = spaceshipRepository.findByNameContainingIgnoreCase(namePart);
        return SpaceshipMapper.toDTO(spaceships);
    }

    public SpaceshipDTO createSpaceship(SpaceshipDTO spaceship) {
        Spaceship save = spaceshipRepository.save(SpaceshipMapper.toEntity(spaceship));
        return SpaceshipMapper.toDTO(save);
    }

    public SpaceshipDTO updateSpaceship(Long id, SpaceshipDTO spaceship) {
        Spaceship existingSpaceship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipServiceNotFoundException("Spaceship with ID: " + id + " not found"));

        existingSpaceship.setName(spaceship.getName());
        existingSpaceship.setModel(spaceship.getModel());
        existingSpaceship.setCreationDate(spaceship.getCreationDate());

        return SpaceshipMapper.toDTO(spaceshipRepository.save(existingSpaceship));
    }

    @CacheEvict(value = "spaceships", key = "#id")
    public String deleteSpaceship(Long id) {
        Spaceship spaceship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipServiceNotFoundException("Spaceship with ID: " + id + " not found"));
        spaceshipRepository.delete(spaceship);
        return "Deleted spaceship with ID: " + id;
    }
}
