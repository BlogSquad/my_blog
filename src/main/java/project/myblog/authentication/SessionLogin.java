package project.myblog.authentication;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthResponse;
import project.myblog.web.dto.SessionMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class SessionLogin implements HandlerInterceptor {
    protected final RestTemplate restTemplate;
    protected final AuthService authService;
    protected final AuthProperties authProperties;

    protected SessionLogin(RestTemplate restTemplate, AuthService authService, AuthProperties authProperties) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.authProperties = authProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // 새로 로그인
        if (session.getAttribute("member") == null) {
//            String authorizationCode = request.getParameter("code");
//            String accessToken = requestAccessToken(authorizationCode);
//            OAuthResponse oAuthResponse = requestApiMeUri(accessToken);
            String authorizationCode = requestAuthorizationCode(request);
            String accessToken = requestAccessToken(authorizationCode);
            OAuthResponse oAuthResponse = requestApiMeUri(accessToken);

            SessionMember sessionMember = authService.login(oAuthResponse);
            afterAuthentication(request, response, sessionMember);
            System.out.println("새로 로그인");
            return false;
        }

        // 기존 세션으로 로그인 유지
        System.out.println("세션 로그인");
        return true;
    }

    public abstract String requestAuthorizationCode(HttpServletRequest request);

    public abstract String requestAccessToken(String authorizationCode);

    public abstract OAuthResponse requestApiMeUri(String accessToken);

    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, SessionMember sessionMember) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("member", sessionMember);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }

//    private String requestAccessToken(String code) {
//        URI uri = UriComponentsBuilder.fromHttpUrl(authProperties.getAccessTokenUri())
//                .queryParam("grant_type", authProperties.getGrantType())
//                .queryParam("client_id", authProperties.getClientId())
//                .queryParam("client_secret", authProperties.getClientSecret())
//                .queryParam("code", code)
//                .build()
//                .toUri();
//
//        String accessToken = restTemplate.getForObject(uri, NaverAccessToken.class)
//                .getAccessToken();
//
//        if (accessToken == null) {
//            throw new IllegalArgumentException("accessToken을 발급받지 못했습니다.");
//        }
//        return accessToken;
//    }
//
//    private OAuthResponse requestApiMeUri(String accessToken) {
//        HttpHeaders header = new HttpHeaders();
//        header.add("Authorization", "Bearer " + accessToken);
//
//        return restTemplate.postForObject(authProperties.getApiMeUri(), new HttpEntity<>(header), OAuthResponse.class);
//    }
}
