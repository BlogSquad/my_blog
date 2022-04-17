package project.myblog.exception;

public class NotExistsMemberException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotExistsMemberException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
