package project.myblog.auth.oauthAuthentication.session;

import org.springframework.web.client.RestTemplate;
import project.myblog.auth.oauthAuthentication.OAuthAuthentication;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class OAuthSessionAuthentication extends OAuthAuthentication {
    protected OAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate) {
        super(memberService, restTemplate);
    }

    @Override
    protected boolean isNewLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null;
    }

    protected void afterAuthenticate(HttpServletRequest request, HttpServletResponse response, LoginMember loginMember) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
