package com.w2m.spaceships_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.function.Consumer;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SpaceshipApiException.class)
    public void handleSpaceshipApiExceptions(SpaceshipApiException ex) {
        logByMessageByLevel(ex.getMessage(), ex.level);
    }

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception ex) {
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