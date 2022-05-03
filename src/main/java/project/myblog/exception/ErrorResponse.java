package project.myblog.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;

    private ErrorResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse createBusiness(ErrorCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getCode(), exceptionCode.getMessage());
    }

    public static ErrorResponse createBind(HttpStatus status, String code, String message) {
        return new ErrorResponse(status, code, message);
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
