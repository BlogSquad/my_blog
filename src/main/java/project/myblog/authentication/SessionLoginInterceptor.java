package project.myblog.authentication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.oauth.AuthProperties;
import project.myblog.oauth.NaverAccessToken;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class SessionLoginInterceptor extends SessionLogin {

    public SessionLoginInterceptor(RestTemplate restTemplate, AuthService authService, AuthProperties authProperties) {
        super(restTemplate, authService, authProperties);
    }

    @Override
    public String requestAuthorizationCode(HttpServletRequest request) {
        return request.getParameter("code");
    }

    @Override
    public String requestAccessToken(String authorizationCode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(authProperties.getAccessTokenUri())
                .queryParam("grant_type", authProperties.getGrantType())
                .queryParam("client_id", authProperties.getClientId())
                .queryParam("client_secret", authProperties.getClientSecret())
                .queryParam("code", authorizationCode)
                .build()
                .toUri();

        String accessToken = restTemplate.getForObject(uri, NaverAccessToken.class)
                .getAccessToken();

        if (accessToken == null) {
            throw new IllegalArgumentException("accessToken을 발급받지 못했습니다.");
        }
        return accessToken;
    }

    @Override
    public OAuthApiResponse requestApiMeUri(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);

        return restTemplate.postForObject(authProperties.getApiMeUri(), new HttpEntity<>(header), OAuthApiResponse.class);
    }
}
