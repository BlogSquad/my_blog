package project.myblog.authentication.session;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.oauth.AuthProperties;
import project.myblog.oauth.NaverAccessToken;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

public class NaverSessionOAuthLoginInterceptor extends SessionOAuthLoginInterceptor {
    private final RestTemplate restTemplate;
    private final AuthProperties authProperties;

    public NaverSessionOAuthLoginInterceptor(RestTemplate restTemplate, AuthProperties authProperties, AuthService authService) {
        super(authService);
        this.restTemplate = restTemplate;
        this.authProperties = authProperties;
    }

    @Override
    public String requestAccessToken(HttpServletRequest request, HttpServletResponse response) {
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
    public OAuthApiResponse requestUserEmail(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);

        return restTemplate.postForObject(authProperties.getApiMeUri(), new HttpEntity<>(header), OAuthApiResponse.class);
    }

    @Override
    public boolean isAccessToken(HttpServletRequest request, HttpServletResponse response, String accessToken) throws ServletException, IOException, AuthenticationException {
        if (accessToken != null) {
            return true;
        }

        request.setAttribute("message", "로그인이 필요합니다.");
        request.setAttribute("exception", "AuthenticationException");
        request.getRequestDispatcher("/api/error").forward(request, response);
        return false;
    }
}
