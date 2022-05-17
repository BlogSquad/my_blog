package project.myblog.auth.oauthAuthentication.intercpetor;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.auth.oauthAuthentication.Logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LogoutInterceptor implements HandlerInterceptor {
    private final List<Logout> logouts;

    public LogoutInterceptor(List<Logout> logouts) {
        this.logouts = logouts;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logouts.stream()
                .filter(logout -> logout.isSupported(request, response))
                .findFirst()
                .ifPresent(logout -> logout.logout(request, response));

        return false;
    }
}
