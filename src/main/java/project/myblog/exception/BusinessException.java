package project.myblog.exception;

public class BusinessException extends RuntimeException {
    private final ErrorCode exceptionCode;

    public BusinessException(ErrorCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ErrorCode getErrorCode() {
        return exceptionCode;
    }
}
