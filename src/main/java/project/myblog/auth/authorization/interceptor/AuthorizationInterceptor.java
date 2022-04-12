package project.myblog.auth.authorization.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.auth.authorization.Authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthorizationInterceptor implements HandlerInterceptor {
    private final List<Authorization> authorizations;

    public AuthorizationInterceptor(List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        authorizations.stream()
                .filter(authorization -> authorization.isSupported(request, response))
                .findFirst()
                .ifPresent(authorization -> authorization.authorize(request, response));

        return true;
    }
}
