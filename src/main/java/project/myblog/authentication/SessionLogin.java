package project.myblog.authentication;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;
import project.myblog.web.dto.SessionMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class SessionLogin implements HandlerInterceptor {
    protected final RestTemplate restTemplate;
    protected final AuthService authService;
    protected final AuthProperties authProperties;

    protected SessionLogin(RestTemplate restTemplate, AuthService authService, AuthProperties authProperties) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.authProperties = authProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session.getAttribute("loginMember") == null) {
            String authorizationCode = getAuthorizationCode(request);
            String accessToken = requestAccessToken(authorizationCode, request, response);
            if (accessToken == null) {
                request.setAttribute("message", "로그인이 필요한 서비스");
                request.setAttribute("exception", "AuthenticationException");
                request.getRequestDispatcher("/api/error").forward(request, response);
                return false;
            }
            OAuthApiResponse oAuthApiResponse = requestApiMeUri(accessToken);

            SessionMember sessionMember = authService.login(oAuthApiResponse);
            afterAuthentication(request, response, sessionMember);
            return false;
        }

        return true;
    }

    public abstract String getAuthorizationCode(HttpServletRequest request);

    public abstract String requestAccessToken(String authorizationCode, HttpServletRequest request, HttpServletResponse response);

    public abstract OAuthApiResponse requestApiMeUri(String accessToken);

    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
