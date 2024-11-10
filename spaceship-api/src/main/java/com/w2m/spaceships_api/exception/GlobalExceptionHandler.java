package com.w2m.spaceships_api.exception;

import com.w2m.spaceships_api.exception.service.SpaceShipServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Service exceptions
    @ExceptionHandler(SpaceShipServiceException.class)
    public ResponseEntity<String> handleServiceExceptions(SpaceShipServiceException ex) {
        logByMessageByLevel(ex.getMessage(), Level.INFO);
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public void handleGeneralException(Exception ex) {
        logByMessageByLevel(ex.getMessage(), Level.ERROR);
    }

    private void logByMessageByLevel(String message, Level level) {
        loggers.getOrDefault(level, log::info).accept(message);
    }

    private static final Map<Level, Consumer<String>> loggers = Map.of(
            Level.INFO, log::info,
            Level.WARN, log::warn,
            Level.ERROR, log::error
    );

}

