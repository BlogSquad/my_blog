package project.myblog.authentication;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OAuthLoginInterceptor implements HandlerInterceptor {
    private List<OAuthLogin> oAuthLogins;

    public OAuthLoginInterceptor(List<OAuthLogin> oAuthLogins) {
        this.oAuthLogins = oAuthLogins;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        oAuthLogins.stream()
                .filter(oAuthLogin -> oAuthLogin.isSupported(request))
                .findFirst()
                .ifPresent(oAuthLogin -> oAuthLogin.login(request, response));

        return false;
    }
}
