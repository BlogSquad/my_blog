package project.myblog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.myblog.domain.Member;
import project.myblog.oauth.NaverAccessToken;
import project.myblog.repository.MemberRepository;
import project.myblog.web.dto.OAuthResponse;
import project.myblog.web.dto.SessionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;

    public AuthService(RestTemplate restTemplate, MemberRepository memberRepository) {
        this.restTemplate = restTemplate;
        this.memberRepository = memberRepository;
    }

    public void login(String code, HttpServletRequest request) {
        String accessToken = requestAccessToken(code);
        HttpHeaders header = createAuthorizationHeaders(accessToken);

        OAuthResponse oAuthResponse = restTemplate.postForObject(apiMeUri, new HttpEntity<>(header), OAuthResponse.class);

        Member member = memberRepository.findByEmail(oAuthResponse.getEmail());
        SessionResponse sessionResponse;
        if (member == null) {
            Member saveMember = memberRepository.save(new Member(oAuthResponse.getEmail(), oAuthResponse.getName()));
            sessionResponse = new SessionResponse(saveMember);
        } else {
            sessionResponse = new SessionResponse(member);
        }

        HttpSession session = request.getSession();
        session.setAttribute("sessionResponse", sessionResponse);
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
            throw new IllegalArgumentException("accessToken을 발급받지 못했습니다.");
        }
        return accessToken;
    }

    private HttpHeaders createAuthorizationHeaders(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        return header;
    }
}
