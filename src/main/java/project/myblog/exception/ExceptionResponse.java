package project.myblog.exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;

    private ExceptionResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ExceptionResponse createBusiness(ExceptionCode exceptionCode) {
        return new ExceptionResponse(exceptionCode.getStatus(), exceptionCode.getCode(), exceptionCode.getMessage());
    }

    public static ExceptionResponse createBind(HttpStatus status, String code, String message) {
        return new ExceptionResponse(status, code, message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
