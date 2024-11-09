package com.w2m.spaceships_api.repository;

import com.w2m.spaceships_api.model.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
    List<Spaceship> findByNameContaining(String namePart);
}
