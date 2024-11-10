package com.w2m.spaceships_api.exception;

import com.w2m.spaceships_api.exception.service.SpaceShipServiceException;
import com.w2m.spaceships_api.utils.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.w2m.spaceships_api.utils.Logger.Level.ERROR;
import static com.w2m.spaceships_api.utils.Logger.Level.INFO;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Service exceptions
    @ExceptionHandler(SpaceShipServiceException.class)
    public ResponseEntity<String> handleServiceExceptions(SpaceShipServiceException ex) {
        Logger.log(ex.getMessage(), INFO);
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public void handleGeneralException(Exception ex) {
        Logger.log(ex.getMessage(), ERROR);
    }

}

