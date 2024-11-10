package com.w2m.spaceships_api.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SpaceshipDTO {

    private Long id;

    private String name;

    private String model;

    private LocalDate creationDate;
}