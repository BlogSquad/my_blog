package project.myblog.exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;

    public ExceptionResponse(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
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
