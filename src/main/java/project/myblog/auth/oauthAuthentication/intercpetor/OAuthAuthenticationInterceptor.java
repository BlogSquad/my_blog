package project.myblog.auth.oauthAuthentication.intercpetor;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.auth.oauthAuthentication.OAuthAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OAuthAuthenticationInterceptor implements HandlerInterceptor {
    private final List<OAuthAuthentication> oAuthAuthentications;

    public OAuthAuthenticationInterceptor(List<OAuthAuthentication> oAuthAuthentications) {
        this.oAuthAuthentications = oAuthAuthentications;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        oAuthAuthentications.stream()
                .filter(oAuthAuthentication -> oAuthAuthentication.isSupported(request))
                .findFirst()
                .ifPresent(oAuthAuthentication -> oAuthAuthentication.login(request, response));

        return false;
    }
}
