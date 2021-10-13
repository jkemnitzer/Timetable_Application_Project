package de.hofuniversity.minf.stundenplaner.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author nlehmann
 *
 * Fallback Handler for all unhandled Exceptions to prevent info leaks
 */
@ControllerAdvice
public class FallbackExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Object> exception(Throwable exception) {
        return new ResponseEntity<>("Unexpected Error", HttpStatus.BAD_REQUEST);
    }

}
