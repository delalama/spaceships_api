package com.w2m.spaceships_api.exception.service;

import org.springframework.http.HttpStatus;

public class SpaceshipServiceNotFoundException extends SpaceShipServiceException {

    public SpaceshipServiceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
