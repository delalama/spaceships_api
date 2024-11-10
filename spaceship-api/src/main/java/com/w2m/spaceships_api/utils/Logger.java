package com.w2m.spaceships_api.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * A centralized logging utility for the application, providing a consistent way to log messages at different levels (INFO, WARN, ERROR).
 * This class simplifies logging by offering a single method to log messages based on the specified log level.
 * It uses Lombok's {@code @Slf4j} to automatically handle logging, reducing boilerplate code.
 * The {@link Logger} class is intended for use throughout the application to ensure uniform logging practices.
 * One of its key features is the ability to log custom messages, which can include custom formatting or even icons, enhancing the clarity and consistency of log outputs.
 * <p>
 * Example usage:
 * Logger.log("Info message", Logger.Level.INFO);
 * Logger.log("Warning message", Logger.Level.WARN);
 * Logger.log("Error message", Logger.Level.ERROR);
 * Logger.log("Custom message with icon", Logger.Level.CUSTOM);
 */
@Slf4j
public class Logger {

    public static void log(String message, Level level) {
        switch (level) {
            case WARN -> log.warn(message);
            case ERROR -> log.error(message);
            case CUSTOM -> logCustom(message);
            default -> log.info(message);
        }
    }

    private static void logCustom(String message) {
        String customMessage = "🚀 " + message;
        log.info(customMessage);
    }

    public enum Level {
        INFO,
        WARN,
        ERROR,
        CUSTOM
    }

}