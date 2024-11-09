package com.w2m.spaceships_api.exception;

import org.apache.logging.log4j.Level;

public class SpaceshipApiException extends RuntimeException {

    Level level;

    public SpaceshipApiException(String message, Level level) {
        super(message);
        this.level = level;
    }
}
