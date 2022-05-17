package project.myblog.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public enum ErrorCode {
    MEMBER_INVALID(BAD_REQUEST, "MEMBER_001", "존재하지 않는 회원입니다."),
    MEMBER_AUTHORIZATION(UNAUTHORIZED, "MEMBER_002", "로그인 인증이 필요합니다."),
    MEMBER_AUTHENTICATION(UNAUTHORIZED, "MEMBER_003", "인증되지 않는 사용자입니다."),
    POST_INVALID(NOT_FOUND, "POST_001", "존재하지 않는 포스트입니다."),
    POST_AUTHORIZATION(FORBIDDEN, "POST_002", "포스트에 대한 권한이 없습니다."),
    COMMENT_AUTHORIZATION(FORBIDDEN, "POST_003", "댓글에 대한 권한이 없습니다."),
    COMMENT_INVALID(NOT_FOUND, "POST_004", "존재하지 않는 댓글입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
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
