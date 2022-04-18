package project.myblog.config;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ValidationResult {
    private final List<FieldErrorDetail> errors;

    public ValidationResult(Errors errors, MessageSource messageSource, Locale locale) {
        this.errors = errors.getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDetail(error, messageSource, locale))
                .collect(Collectors.toList());
    }

    public List<FieldErrorDetail> getErrors() {
        return errors;
    }
}
