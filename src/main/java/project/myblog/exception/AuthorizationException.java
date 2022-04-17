package project.myblog.exception;

public class AuthorizationException extends RuntimeException {
    private final ErrorCode errorCode;

    public AuthorizationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
