package project.myblog.authentication.session;

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

public class NaverOAuthSessionAuthentication extends OAuthSessionAuthentication {
    private static final String NAVER_OAUTH_URI = "naver";
    private final AuthProperties authProperties;

    public NaverOAuthSessionAuthentication(AuthService authService, RestTemplate restTemplate, AuthProperties authProperties) {
        super(authService, restTemplate);
        this.authProperties = authProperties;
    }

    public boolean isSupported(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains(NAVER_OAUTH_URI);
    }

    @Override
    protected String requestAccessToken(HttpServletRequest request) {
        String authorizationCode = request.getParameter("code");
        URI uri = UriComponentsBuilder.fromHttpUrl(authProperties.getAccessTokenUri())
                .queryParam("grant_type", authProperties.getGrantType())
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

        return restTemplate.postForObject(authProperties.getApiMeUri(), new HttpEntity<>(header), OAuthApiResponse.class);
    }
}
