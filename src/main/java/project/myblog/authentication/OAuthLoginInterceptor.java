package project.myblog.authentication;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;
import project.myblog.web.dto.SessionMember;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class OAuthLoginInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    protected OAuthLoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException, AuthenticationException {
        HttpSession session = request.getSession();

//        isAuthenticated();

        if (session.getAttribute("loginMember") == null) {
            String accessToken = requestAccessToken(request, response);
            if (isAccessToken(request, response, accessToken)) {
                OAuthApiResponse oAuthApiResponse = requestUserEmail(accessToken);
                SessionMember sessionMember = authService.login(oAuthApiResponse);

                afterAuthentication(request, response, sessionMember);
                return false;
            }

            return false;
        }

        return true;
    }

    protected boolean isAuthenticated() throws AuthenticationException {
//        throw new AuthenticationException("이병덕");
        throw new IllegalStateException("인증 안됨");
//        return false;
    }

    protected abstract String requestAccessToken(HttpServletRequest request, HttpServletResponse response);

    protected abstract OAuthApiResponse requestUserEmail(String accessToken);

    protected abstract void afterAuthentication(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember);

    protected abstract boolean isAccessToken(HttpServletRequest request, HttpServletResponse response, String accessToken) throws ServletException, IOException, AuthenticationException;
}
