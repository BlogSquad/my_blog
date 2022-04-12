package project.myblog.auth.authorization.session;

import project.myblog.auth.authorization.Authorization;
import project.myblog.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionAuthorization implements Authorization {
    @Override
    public boolean isSupported(HttpServletRequest request, HttpServletResponse response) {
        // TODO: 2022/04/12 session, jwt 구분 필요
        return true;
    }

    @Override
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) == null) {
            throw new AuthorizationException("로그인이 필요합니다.");
        }
    }
}
