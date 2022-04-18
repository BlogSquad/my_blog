package project.myblog.config;

import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.util.Locale;

public class FieldErrorDetail {
    private final String objectName;
    private final String field;
    private final String code;
    private final String message;

    public FieldErrorDetail(FieldError fieldError, MessageSource messageSource, Locale locale) {
        this.objectName = fieldError.getObjectName();
        this.field = fieldError.getField();
        this.code = fieldError.getCode();
        this.message = messageSource.getMessage(fieldError, locale);
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
