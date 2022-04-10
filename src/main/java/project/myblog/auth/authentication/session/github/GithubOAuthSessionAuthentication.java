package project.myblog.auth.authentication.session.github;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.authentication.session.OAuthSessionAuthentication;
import project.myblog.auth.dto.github.GithubAccessToken;
import project.myblog.service.AuthService;
import project.myblog.auth.dto.github.GithubOAuthApiResponse;
import project.myblog.auth.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;

import static project.myblog.auth.dto.SocialType.GITHUB;

public class GithubOAuthSessionAuthentication extends OAuthSessionAuthentication {
    private final AuthProperties authProperties;

    public GithubOAuthSessionAuthentication(AuthService authService, RestTemplate restTemplate, AuthProperties authProperties) {
        super(authService, restTemplate);
        this.authProperties = authProperties;
    }

    public boolean isSupported(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains(GITHUB.getServiceName());
    }

    @Override
    protected String requestAccessToken(HttpServletRequest request) {
        String authorizationCode = request.getParameter("code");
        URI uri = UriComponentsBuilder.fromHttpUrl(GITHUB.getAccessTokenUri())
                .queryParam("redirect_uri", GITHUB.getRedirectUri())
                .queryParam("client_id", authProperties.getClientId())
                .queryParam("client_secret", authProperties.getClientSecret())
                .queryParam("code", authorizationCode)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, GithubAccessToken.class)
                .getAccessToken();
    }

    @Override
    protected OAuthApiResponse requestUserInfo(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "token " + accessToken);

        GithubOAuthApiResponse[] body = restTemplate.exchange(
                GITHUB.getApiMeUri(),
                HttpMethod.GET,
                new HttpEntity<>(header),
                GithubOAuthApiResponse[].class
        ).getBody();

        return Arrays.stream(body)
                .findFirst()
                .get();
    }
}
