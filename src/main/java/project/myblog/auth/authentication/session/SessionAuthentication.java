package project.myblog.auth.authentication.session;

import project.myblog.auth.authentication.Authentication;
import project.myblog.exception.BusinessException;
import project.myblog.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionAuthentication implements Authentication {
    @Override
    public boolean isSupported(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) == null) {
            throw new BusinessException(ErrorCode.MEMBER_AUTHORIZATION);
        }
    }
}
