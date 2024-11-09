package com.w2m.spaceships_api.service;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.repository.SpaceshipRepository;
import com.w2m.spaceships_api.exception.SpaceshipApiException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    public Page<Spaceship> getAllSpaceships(int page, int size) {
        return spaceshipRepository.findAll(PageRequest.of(page, size));
    }

    @Cacheable(value = "spaceships", key = "#id")
    public Spaceship getSpaceshipById(Long id) {
        return spaceshipRepository.findById(id).orElseThrow(() -> new SpaceshipApiException("Spaceship with ID: " + id + " not found", Level.WARN));
    }

    public List<Spaceship> getSpaceshipsByName(String namePart) {
        return spaceshipRepository.findByNameContaining(namePart);
    }

    public Spaceship createSpaceship(Spaceship spaceship) {
        return spaceshipRepository.save(spaceship);
    }

    public Spaceship updateSpaceship(Long id, Spaceship spaceship) {
        spaceship.setId(id);
        return spaceshipRepository.save(spaceship);
    }

    public void deleteSpaceship(Long id) {
        spaceshipRepository.deleteById(id);
    }
}
