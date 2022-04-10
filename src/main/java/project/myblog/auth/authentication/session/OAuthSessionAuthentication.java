package project.myblog.auth.authentication.session;

import org.springframework.web.client.RestTemplate;
import project.myblog.auth.authentication.OAuthAuthentication;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class OAuthSessionAuthentication extends OAuthAuthentication {
    protected OAuthSessionAuthentication(AuthService authService, RestTemplate restTemplate) {
        super(authService, restTemplate);
    }

    @Override
    protected boolean isNewLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null;
    }

    protected void afterAuthenticate(HttpServletRequest request, HttpServletResponse response, LoginMember sessionMember) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
