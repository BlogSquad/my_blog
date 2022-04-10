package project.myblog.authorization.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getSession(false) == null) {
            throw new AuthorizationException("로그인이 필요합니다.");
        }

        return true;
    }
}
