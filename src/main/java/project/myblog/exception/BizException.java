package project.myblog.exception;

public class BizException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    public BizException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }
}
