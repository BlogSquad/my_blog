package project.myblog.auth.oauthAuthentication.session.naver;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.auth.oauthAuthentication.session.OAuthSessionAuthentication;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.auth.dto.SocialType;
import project.myblog.auth.dto.naver.NaverAccessToken;
import project.myblog.auth.dto.naver.NaverOAuthApiResponse;
import project.myblog.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;

public class NaverOAuthSessionAuthentication extends OAuthSessionAuthentication {
    private final AuthProperties authProperties;

    public NaverOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate,
                                           AuthProperties authProperties) {
        super(memberService, restTemplate);
        this.authProperties = authProperties;
    }

    public boolean isSupported(HttpServletRequest request) {
        String loginUri = SESSION_LOGIN_URI + "/" + SocialType.NAVER.getServiceName();
        return loginUri.equals(request.getRequestURI());
    }

    @Override
    protected String requestAccessToken(HttpServletRequest request) {
        String authorizationCode = request.getParameter("code");
        URI uri = UriComponentsBuilder.fromHttpUrl(NAVER.getAccessTokenUri())
                .queryParam("grant_type", NAVER.getGrantType())
                .queryParam("client_id", authProperties.getNaverClientId())
                .queryParam("client_secret", authProperties.getNaverClientSecret())
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
