package project.myblog.exception;

public class NotExistsMemberException extends RuntimeException {
    public NotExistsMemberException(String message) {
        super(message);
    }
}
