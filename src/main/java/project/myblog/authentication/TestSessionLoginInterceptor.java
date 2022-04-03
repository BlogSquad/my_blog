package project.myblog.authentication;

import org.springframework.web.client.RestTemplate;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestSessionLoginInterceptor extends SessionLogin {
    public TestSessionLoginInterceptor(RestTemplate restTemplate, AuthService authService, AuthProperties authProperties) {
        super(restTemplate, authService, authProperties);
    }

    @Override
    public String requestAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return "testAccessToken";
    }

    @Override
    public OAuthApiResponse requestApiMeUri(String accessToken) {
        return new OAuthApiResponse(new OAuthApiResponse.Response("monkeyDugi@gmail.com"));
    }
}
