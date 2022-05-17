package project.myblog.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return getStatus() == that.getStatus() && Objects.equals(getCode(), that.getCode()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getCode(), getMessage());
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
