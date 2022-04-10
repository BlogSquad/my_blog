package project.myblog.authentication;

import org.springframework.web.client.RestTemplate;
import project.myblog.exception.AuthenticationException;
import project.myblog.service.AuthService;
import project.myblog.web.dto.LoginMember;
import project.myblog.web.dto.OAuthApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class OAuthAuthentication {

    private final AuthService authService;
    protected final RestTemplate restTemplate;

    protected OAuthAuthentication(AuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }

    public abstract boolean isSupported(HttpServletRequest request);

    public void login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            String accessToken = requestAccessToken(request);
            if (isAccessToken(request, response, accessToken)) {
                authenticate(request, response, accessToken);
            }
        }
    }

    private boolean isAccessToken(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        if (accessToken != null) {
            return true;
        }

        request.setAttribute("message", "인증되지 않는 사용자입니다.");
        request.setAttribute("exception", AuthenticationException.class);
        try {
            request.getRequestDispatcher("/api/error").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        OAuthApiResponse oAuthApiResponse = requestUserInfo(accessToken);
        LoginMember sessionMember = authService.login(oAuthApiResponse);
        afterAuthenticate(request, response, sessionMember);
    }

    protected abstract String requestAccessToken(HttpServletRequest request);

    protected abstract OAuthApiResponse requestUserInfo(String accessToken);

    protected abstract void afterAuthenticate(HttpServletRequest request, HttpServletResponse response, LoginMember sessionMember);
}

