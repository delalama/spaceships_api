package com.w2m.spaceships_api.exception.service;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpaceShipServiceException extends RuntimeException {

    final HttpStatus httpStatus;

    public SpaceShipServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
