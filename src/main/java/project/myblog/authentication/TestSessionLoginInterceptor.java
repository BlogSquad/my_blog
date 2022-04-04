package project.myblog.authentication;

import org.springframework.web.client.RestTemplate;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestSessionLoginInterceptor extends SessionLogin {
    private static final String AUTHORIZATION_CODE = "authorizationCode";
    public TestSessionLoginInterceptor(RestTemplate restTemplate, AuthService authService, AuthProperties authProperties) {
        super(restTemplate, authService, authProperties);
    }

    @Override
    public String requestAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        if (AUTHORIZATION_CODE.equals(code)) {
            return "testAccessToken";
        }
        return null;
    }

    @Override
    public OAuthApiResponse requestUserEmail(String accessToken) {
        return new OAuthApiResponse(new OAuthApiResponse.Response("monkeyDugi@gmail.com"));
    }
}
