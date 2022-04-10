package project.myblog.auth.authentication.session.naver;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.auth.authentication.session.OAuthSessionAuthentication;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.naver.NaverAccessToken;
import project.myblog.service.AuthService;
import project.myblog.auth.dto.naver.NaverOAuthApiResponse;
import project.myblog.auth.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static project.myblog.auth.dto.SocialType.NAVER;

public class NaverOAuthSessionAuthentication extends OAuthSessionAuthentication {
    private final AuthProperties authProperties;

    public NaverOAuthSessionAuthentication(AuthService authService, RestTemplate restTemplate, AuthProperties authProperties) {
        super(authService, restTemplate);
        this.authProperties = authProperties;
    }

    public boolean isSupported(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains(NAVER.getServiceName());
    }

    @Override
    protected String requestAccessToken(HttpServletRequest request) {
        String authorizationCode = request.getParameter("code");
        URI uri = UriComponentsBuilder.fromHttpUrl(NAVER.getAccessTokenUri())
                .queryParam("grant_type", NAVER.getGrantType())
                .queryParam("client_id", authProperties.getClientId())
                .queryParam("client_secret", authProperties.getClientSecret())
                .queryParam("code", authorizationCode)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, NaverAccessToken.class)
                .getAccessToken();
    }

    @Override
    protected OAuthApiResponse requestUserInfo(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);

        return restTemplate.exchange(NAVER.getApiMeUri(), HttpMethod.GET, new HttpEntity<>(header), NaverOAuthApiResponse.class)
                .getBody();
    }
}
