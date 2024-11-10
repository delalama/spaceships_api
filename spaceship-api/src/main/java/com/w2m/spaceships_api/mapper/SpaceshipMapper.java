package com.w2m.spaceships_api.mapper;

import com.w2m.spaceships_api.model.Spaceship;
import com.w2m.spaceships_api.model.SpaceshipDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpaceshipMapper {

    public static SpaceshipDTO toDTO(Spaceship spaceship) {
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

    public static List<SpaceshipDTO> toDTO(List<Spaceship> spaceships) {
        return spaceships.stream().map(SpaceshipMapper::toDTO).collect(Collectors.toList());
    }

    public static Spaceship toEntity(SpaceshipDTO spaceshipDTO) {
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

    public static List<Spaceship> toEntity(List<SpaceshipDTO> spaceships) {
        return spaceships.stream().map(SpaceshipMapper::toEntity).collect(Collectors.toList());
    }
}