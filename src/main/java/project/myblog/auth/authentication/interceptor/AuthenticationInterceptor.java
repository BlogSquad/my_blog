package project.myblog.auth.authentication.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.auth.authentication.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private final List<Authentication> authentications;

    public AuthenticationInterceptor(List<Authentication> authentications) {
        this.authentications = authentications;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        authentications.stream()
                .filter(authentication -> authentication.isSupported(request, response))
                .findFirst()
                .ifPresent(authentication -> authentication.authorize(request, response));

        return true;
    }
}
