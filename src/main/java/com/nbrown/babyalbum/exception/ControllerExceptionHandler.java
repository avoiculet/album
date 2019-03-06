package com.nbrown.babyalbum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by avoiculet on 06/03/2019.
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    //TODO: Handle IOExceptions and handle file does not exist.

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElement() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity handleIOException() {
        return new ResponseEntity("A problem has occurred, please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalAccessError.class)
    public ResponseEntity handleIllegalAccessError(IllegalAccessError exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
