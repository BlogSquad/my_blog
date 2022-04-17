package project.myblog.exception;

public class AuthenticationException extends RuntimeException {
    private final ErrorCode errorCode;
    public AuthenticationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
