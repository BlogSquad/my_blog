package project.myblog.authentication.session;

import project.myblog.authentication.OAuthLoginInterceptor;
import project.myblog.service.AuthService;
import project.myblog.web.dto.SessionMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class SessionOAuthLoginInterceptor extends OAuthLoginInterceptor {
    protected SessionOAuthLoginInterceptor(AuthService authService) {
        super(authService);
    }

    protected void afterAuthentication(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
