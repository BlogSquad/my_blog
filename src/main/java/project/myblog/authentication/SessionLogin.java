package project.myblog.authentication;

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

        // 새로 로그인
        if (session.getAttribute("member") == null) {
            String authorizationCode = requestAuthorizationCode(request);
            String accessToken = requestAccessToken(authorizationCode);
            OAuthApiResponse oAuthApiResponse = requestApiMeUri(accessToken);

            SessionMember sessionMember = authService.login(oAuthApiResponse);
            afterAuthentication(request, response, sessionMember);
            System.out.println("새로 로그인");
            return false;
        }

        // 기존 세션으로 로그인 유지
        System.out.println("세션 로그인");
        return true;
    }

    public abstract String requestAuthorizationCode(HttpServletRequest request);

    public abstract String requestAccessToken(String authorizationCode);

    public abstract OAuthApiResponse requestApiMeUri(String accessToken);

    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("member", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}
