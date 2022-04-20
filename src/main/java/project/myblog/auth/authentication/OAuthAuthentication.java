package project.myblog.auth.authentication;

import org.springframework.web.client.RestTemplate;
import project.myblog.auth.dto.LoginMember;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.exception.ExceptionCode;
import project.myblog.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class OAuthAuthentication {

    private final MemberService memberService;
    protected final RestTemplate restTemplate;

    protected OAuthAuthentication(MemberService memberService, RestTemplate restTemplate) {
        this.memberService = memberService;
        this.restTemplate = restTemplate;
    }

    public abstract boolean isSupported(HttpServletRequest request);

    public void login(HttpServletRequest request, HttpServletResponse response) {
        if (isNewLogin(request)) {
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

        request.setAttribute("exceptionType", ExceptionCode.MEMBER_AUTHENTICATION);
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
        LoginMember sessionMember = memberService.signUp(oAuthApiResponse.getEmail());
        afterAuthenticate(request, response, sessionMember);
    }

    protected abstract boolean isNewLogin(HttpServletRequest request);

    protected abstract String requestAccessToken(HttpServletRequest request);

    protected abstract OAuthApiResponse requestUserInfo(String accessToken);

    protected abstract void afterAuthenticate(HttpServletRequest request, HttpServletResponse response,
                                              LoginMember sessionMember);
}

