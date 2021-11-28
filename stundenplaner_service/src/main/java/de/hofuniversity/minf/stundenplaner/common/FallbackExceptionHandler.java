package de.hofuniversity.minf.stundenplaner.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author nlehmann
 * Fallback Handler for all unhandled Exceptions to prevent info leaks
 */
@Slf4j
@ControllerAdvice
public class FallbackExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Object> exception(Throwable exception) {
        log.error("Unexpected Exception occurred", exception);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exception(NotFoundException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = SimpleAuthException.class)
    public ResponseEntity<Object> exception(SimpleAuthException exception){
        log.warn(exception.getMessage());
        return ResponseEntity.status(401).build();
    }

}
