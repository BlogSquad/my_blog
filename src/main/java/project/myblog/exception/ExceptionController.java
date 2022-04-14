package project.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handlerAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handlerIllegalStateException(AuthorizationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handlerIllegalStateException(ConstraintViolationException e) {
        String collect = extractMessage(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(collect);
    }

    private String extractMessage(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .toString();
    }
}
