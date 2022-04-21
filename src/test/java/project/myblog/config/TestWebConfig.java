package project.myblog.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.client.RestTemplate;
import project.myblog.auth.oauthAuthentication.OAuthAuthentication;
import project.myblog.auth.oauthAuthentication.intercpetor.OAuthAuthenticationInterceptor;
import project.myblog.auth.oauthAuthentication.session.OAuthSessionAuthentication;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.auth.dto.SocialType;
import project.myblog.auth.dto.github.GithubOAuthApiResponse;
import project.myblog.auth.dto.naver.NaverOAuthApiResponse;
import project.myblog.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@TestConfiguration(value = "webConfig")
public class TestWebConfig extends WebConfig {
    public TestWebConfig(MemberService memberService, AuthProperties authProperties) {
        super(memberService, authProperties);
    }

    protected OAuthAuthenticationInterceptor oAuthAuthenticationInterceptor() {
        List<OAuthAuthentication> oAuthAuthentications = new ArrayList<>();
        oAuthAuthentications.add(new TestNaverOAuthSessionAuthentication(memberService, restTemplate()));
        oAuthAuthentications.add(new TestGithubOAuthSessionAuthentication(memberService, restTemplate()));

        return new OAuthAuthenticationInterceptor(oAuthAuthentications);
    }

    public abstract static class TestAbstractOAuthSessionAuthentication extends OAuthSessionAuthentication {
        public static final String AUTHORIZATION_CODE = "authorizationCode";

        public TestAbstractOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate) {
            super(memberService, restTemplate);
        }

        @Override
        protected String requestAccessToken(HttpServletRequest request) {
            String code = request.getParameter("code");
            if (AUTHORIZATION_CODE.equals(code)) {
                return "testAccessToken";
            }
            return null;
        }
    }

    static class TestNaverOAuthSessionAuthentication extends TestAbstractOAuthSessionAuthentication {
        public TestNaverOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate) {
            super(memberService, restTemplate);
        }

        public boolean isSupported(HttpServletRequest request) {
            String loginUri = SESSION_LOGIN_URI + "/" + SocialType.NAVER.getServiceName();
            return loginUri.equals(request.getRequestURI());
        }

        @Override
        protected OAuthApiResponse requestUserInfo(String accessToken) {
            return new NaverOAuthApiResponse(new NaverOAuthApiResponse.Response("monkeyDugi@gmail.com"));
        }
    }

    static class TestGithubOAuthSessionAuthentication extends TestAbstractOAuthSessionAuthentication {
        public TestGithubOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate) {
            super(memberService, restTemplate);
        }

        public boolean isSupported(HttpServletRequest request) {
            String loginUri = SESSION_LOGIN_URI + "/" + SocialType.GITHUB.getServiceName();
            return loginUri.equals(request.getRequestURI());
        }

        @Override
        protected OAuthApiResponse requestUserInfo(String accessToken) {
            return new GithubOAuthApiResponse("monkeyDugi@github.com");
        }
    }
}
