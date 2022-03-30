package project.myblog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.oauth.NaverAccessToken;
import project.myblog.web.dto.OAuthResponse;

import java.net.URI;

@Service
public class AuthService {
    @Value("${oauth.naver.acces_token_uri}")
    private String accessTokenUri;
    @Value("${oauth.naver.grant_type}")
    private String grantType = "authorization_code";
    @Value("${oauth.naver.client_id}")
    private String clientId;
    @Value("${oauth.naver.client_secret_id}")
    private String clientSecret;
    @Value("${oauth.naver.api_me_uri}")
    private String apiMeUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getOAuthEmail(String code) {
        String accessToken = requestAccessToken(code);
        HttpHeaders header = createAuthorizationHeaders(accessToken);

        return restTemplate.postForObject(apiMeUri, new HttpEntity<>(header), OAuthResponse.class).getEmail();
    }

    private HttpHeaders createAuthorizationHeaders(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        return header;
    }

    private String requestAccessToken(String code) {
        URI uri = UriComponentsBuilder.fromHttpUrl(accessTokenUri)
                .queryParam("grant_type", grantType)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .build()
                .toUri();

        String accessToken = restTemplate.getForObject(uri, NaverAccessToken.class)
                .getAccessToken();

        if (accessToken == null) {
            throw new IllegalArgumentException("유효하지 않은 코드입니다.");
        }
        return accessToken;
    }
}
