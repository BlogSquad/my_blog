package project.myblog.auth.authorization.session;

import project.myblog.auth.authorization.Authorization;
import project.myblog.exception.BusinessException;
import project.myblog.exception.ExceptionCode;

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
            throw new BusinessException(ExceptionCode.MEMBER_AUTHORIZATION);
        }
    }
}
