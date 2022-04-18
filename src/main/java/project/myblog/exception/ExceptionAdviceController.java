package project.myblog.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.myblog.config.FieldErrorDetail;
import project.myblog.config.ValidationResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdviceController {
    private final MessageSource messageSource;

    public ExceptionAdviceController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handlerBizException(BusinessException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.createBusiness(e.getErrorCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(extractConstraintViolationExceptionMessage(e));
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ExceptionResponse> handlerMethodArgumentNotValidException(BindException e) {
        ValidationResult validationResult = new ValidationResult(e, messageSource, Locale.KOREA);
        FieldErrorDetail fieldErrorDetail = validationResult.getErrors().get(0);

        ExceptionResponse exceptionResponse = ExceptionResponse.createBind(
                HttpStatus.BAD_REQUEST, fieldErrorDetail.getCode(), fieldErrorDetail.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    private String extractConstraintViolationExceptionMessage(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .toString();
    }
}
