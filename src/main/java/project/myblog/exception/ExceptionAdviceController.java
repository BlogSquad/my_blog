package project.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdviceController {
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ExceptionResponse> handlerBizException(BizException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getErrorCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(extractConstraintViolationExceptionMessage(e));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(extractBindResultFieldErrorMessage(e));
    }

    private String extractConstraintViolationExceptionMessage(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .toString();
    }

    private String extractBindResultFieldErrorMessage(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList())
                .toString();
    }
}
