package de.hofuniversity.minf.stundenplaner.common;

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
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exception(){
        return ResponseEntity.notFound().build();
    }

}
