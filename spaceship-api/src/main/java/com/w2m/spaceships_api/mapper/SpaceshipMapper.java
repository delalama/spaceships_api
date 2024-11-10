package com.w2m.spaceships_api.mapper;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.model.SpaceshipDTO;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipMapper implements EntityMapper<Spaceship, SpaceshipDTO> {

    @Override
    public SpaceshipDTO toDto(Spaceship spaceship) {
        if (spaceship == null) {
            return null;
        }
        return SpaceshipDTO.builder()
                .id(spaceship.getId())
                .name(spaceship.getName())
                .model(spaceship.getModel())
                .creationDate(spaceship.getCreationDate())
                .build();
    }

    @Override
    public Spaceship toEntity(SpaceshipDTO spaceshipDTO) {
        if (spaceshipDTO == null) {
            return null;
        }
        return Spaceship.builder()
                .id(spaceshipDTO.getId())
                .name(spaceshipDTO.getName())
                .model(spaceshipDTO.getModel())
                .creationDate(spaceshipDTO.getCreationDate())
                .build();
    }

}