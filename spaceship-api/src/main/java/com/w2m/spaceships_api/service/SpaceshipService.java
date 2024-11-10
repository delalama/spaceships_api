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

import static com.w2m.spaceships_api.utils.ApiConstants.SPACESHIPS;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipMapper spaceshipMapper;


    public Page<SpaceshipDTO> getAllSpaceships(int page, int size) {
        Page<Spaceship> spaceships = spaceshipRepository.findAll(PageRequest.of(page, size));
        return spaceships.map(spaceshipMapper::toDto);
    }

    @Cacheable(value = SPACESHIPS, key = "#id")
    public Optional<SpaceshipDTO> getSpaceshipById(Long id) {
        Optional<Spaceship> spaceship = spaceshipRepository.findById(id);
        return spaceship.map(spaceshipMapper::toDto);
    }

    @Cacheable(value = SPACESHIPS, key = "#namePart")
    public List<SpaceshipDTO> getSpaceshipsByName(String namePart) {
        List<Spaceship> spaceships = spaceshipRepository.findByNameContainingIgnoreCase(namePart);
        return spaceshipMapper.toDtos(spaceships);
    }

    public SpaceshipDTO createSpaceship(SpaceshipDTO spaceship) {
        Spaceship save = spaceshipRepository.save(spaceshipMapper.toEntity(spaceship));
        return spaceshipMapper.toDto(save);
    }

    public SpaceshipDTO updateSpaceship(Long id, SpaceshipDTO spaceship) {
        Spaceship existingSpaceship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipServiceNotFoundException("Spaceship with ID: " + id + " not found"));

        existingSpaceship.setName(spaceship.getName());
        existingSpaceship.setModel(spaceship.getModel());
        existingSpaceship.setCreationDate(spaceship.getCreationDate());

        return spaceshipMapper.toDto(spaceshipRepository.save(existingSpaceship));
    }

    @CacheEvict(value = SPACESHIPS, key = "#id")
    public String deleteSpaceship(Long id) {
        Spaceship spaceship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new SpaceshipServiceNotFoundException("Spaceship with ID: " + id + " not found"));

        spaceshipRepository.delete(spaceship);
        return "Deleted spaceship with ID: " + id;
    }
}
