package project.myblog.auth.oauthAuthentication.session.github;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.auth.oauthAuthentication.session.OAuthSessionAuthentication;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.auth.dto.github.GithubAccessToken;
import project.myblog.auth.dto.github.GithubOAuthApiResponse;
import project.myblog.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;

import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;

public class GithubOAuthSessionAuthentication extends OAuthSessionAuthentication {
    private final AuthProperties authProperties;

    public GithubOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate,
                                            AuthProperties authProperties) {
        super(memberService, restTemplate);
        this.authProperties = authProperties;
    }

    public boolean isSupported(HttpServletRequest request) {
        String loginUri = SESSION_LOGIN_URI + "/" + GITHUB.getServiceName();
        return loginUri.equals(request.getRequestURI());
    }

    @Override
    protected String requestAccessToken(HttpServletRequest request) {
        String authorizationCode = request.getParameter("code");
        URI uri = UriComponentsBuilder.fromHttpUrl(GITHUB.getAccessTokenUri())
                .queryParam("client_id", authProperties.getGithubClientId())
                .queryParam("client_secret", authProperties.getGithubClientSecret())
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
