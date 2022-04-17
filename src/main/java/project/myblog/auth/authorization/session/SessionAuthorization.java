package project.myblog.auth.authorization.session;

import project.myblog.auth.authorization.Authorization;
import project.myblog.exception.AuthorizationException;
import project.myblog.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionAuthorization implements Authorization {
    @Override
    public boolean isSupported(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) == null) {
            throw new AuthorizationException(ErrorCode.MEMBER_AUTHORIZATION);
        }
    }
}
