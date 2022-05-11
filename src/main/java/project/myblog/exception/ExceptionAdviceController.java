package project.myblog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.myblog.config.FieldErrorDetail;
import project.myblog.config.ValidationResult;
import project.myblog.web.dto.ApiResponse;

import java.util.Locale;

@RestControllerAdvice
public class ExceptionAdviceController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageSource messageSource;

    public ExceptionAdviceController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handlerBizException(BusinessException e) {
        ErrorResponse exceptionResponse = ErrorResponse.createBusiness(e.getErrorCode());

        logger.debug("BusinessException exceptionResponse = {}", exceptionResponse);
        return ResponseEntity.status(exceptionResponse.getStatus()).body(ApiResponse.fail(exceptionResponse));
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ApiResponse<Void>> handlerMethodArgumentNotValidException(BindException e) {
        ValidationResult validationResult = new ValidationResult(e, messageSource, Locale.KOREA);
        FieldErrorDetail fieldErrorDetail = validationResult.getErrors().get(0);

        ErrorResponse exceptionResponse = ErrorResponse.createBind(
                HttpStatus.BAD_REQUEST, fieldErrorDetail.getCode(), fieldErrorDetail.getMessage());

        logger.debug("BindException exceptionResponse = {}", exceptionResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(exceptionResponse));
    }
}
