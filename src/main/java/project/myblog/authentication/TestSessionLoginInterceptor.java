package project.myblog.authentication;

import org.springframework.web.client.RestTemplate;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;

public class TestSessionLoginInterceptor extends SessionLogin {
    public TestSessionLoginInterceptor(RestTemplate restTemplate, AuthService authService, AuthProperties authProperties) {
        super(restTemplate, authService, authProperties);
    }

    @Override
    public String requestAuthorizationCode(HttpServletRequest request) {
        return "TestAuthorizationCode";
    }

    @Override
    public String requestAccessToken(String authorizationCode) {
        return "testAccessToken";
    }

    @Override
    public OAuthApiResponse requestApiMeUri(String accessToken) {
        return new OAuthApiResponse(new OAuthApiResponse.Response("monkeyDugi@gmail.com"));
    }
}
