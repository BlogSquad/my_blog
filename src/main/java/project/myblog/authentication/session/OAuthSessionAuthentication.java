package project.myblog.authentication.session;

import org.springframework.web.client.RestTemplate;
import project.myblog.authentication.OAuthAuthentication;
import project.myblog.service.AuthService;
import project.myblog.web.dto.SessionMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class OAuthSessionAuthentication extends OAuthAuthentication {
    protected OAuthSessionAuthentication(AuthService authService, RestTemplate restTemplate) {
        super(authService, restTemplate);
    }

    protected void afterAuthenticate(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
