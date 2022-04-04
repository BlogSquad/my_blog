package project.myblog.authentication;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;
import project.myblog.web.dto.SessionMember;

import javax.servlet.ServletException;
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

    protected abstract String requestAccessToken(HttpServletRequest request, HttpServletResponse response);

    protected abstract OAuthApiResponse requestUserEmail(String accessToken);

    protected void afterAuthentication(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean isAccessToken(HttpServletRequest request, HttpServletResponse response, String accessToken) throws ServletException, IOException {
        if (accessToken != null) {
            return true;
        }

        request.setAttribute("message", "로그인이 필요합니다.");
        request.setAttribute("exception", "AuthenticationException");
        request.getRequestDispatcher("/api/error").forward(request, response);
        System.out.println("request = " + request);
        return false;
    }
}
