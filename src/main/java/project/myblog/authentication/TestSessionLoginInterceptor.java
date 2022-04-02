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
    public String getAuthorizationCode(HttpServletRequest request) {
        return request.getParameter("code");
    }

    @Override
    public String requestAccessToken(String authorizationCode) {
        if (authorizationCode == null) {
            throw new IllegalArgumentException("accessToken을 발급받지 못했습니다.");
        }
        return "testAccessToken";
    }

    @Override
    public OAuthApiResponse requestApiMeUri(String accessToken) {
        return new OAuthApiResponse(new OAuthApiResponse.Response("monkeyDugi@gmail.com"));
    }
}
