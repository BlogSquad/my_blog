package project.myblog.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.auth.authentication.Logout;
import project.myblog.auth.authentication.session.SessionLogout;
import project.myblog.auth.authorization.Authorization;
import project.myblog.auth.authorization.session.SessionAuthorization;
import project.myblog.auth.dto.LoginMemberArgumentResolver;
import project.myblog.auth.authentication.intercpetor.LogoutInterceptor;
import project.myblog.auth.authentication.OAuthAuthentication;
import project.myblog.auth.authentication.intercpetor.OAuthAuthenticationInterceptor;
import project.myblog.auth.authentication.session.OAuthSessionAuthentication;
import project.myblog.auth.authorization.interceptor.AuthorizationInterceptor;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.SocialType;
import project.myblog.service.member.MemberService;
import project.myblog.auth.dto.github.GithubOAuthApiResponse;
import project.myblog.auth.dto.naver.NaverOAuthApiResponse;
import project.myblog.auth.dto.OAuthApiResponse;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;
import static project.myblog.config.WebConfig.SESSION_LOGOUT_URI;

@TestConfiguration(value = "webConfig")
public class TestWebConfig implements WebMvcConfigurer {
    private final MemberService memberService;
    private final AuthProperties authProperties;

    public TestWebConfig(MemberService memberService, AuthProperties authProperties) {
        this.memberService = memberService;
        this.authProperties = authProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<OAuthAuthentication> oAuthAuthentications = new ArrayList<>();
        oAuthAuthentications.add(new TestNaverOAuthSessionAuthentication(memberService, restTemplate(), authProperties));
        oAuthAuthentications.add(new TestGithubOAuthSessionAuthentication(memberService, restTemplate(), authProperties));

        OAuthAuthenticationInterceptor oAuthAuthenticationInterceptor = new OAuthAuthenticationInterceptor(oAuthAuthentications);

        registry.addInterceptor(oAuthAuthenticationInterceptor)
                .addPathPatterns(SESSION_LOGIN_URI + "/**");

        List<Authorization> authorizations = new ArrayList<>();
        authorizations.add(new SessionAuthorization());

        AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor(authorizations);
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css", "/logout/**", "/login/**",
                        "/docs/**", "/favicon.ico", "/api/error", "/error");

        List<Logout> logouts = new ArrayList<>();
        logouts.add(new SessionLogout());

        LogoutInterceptor logoutInterceptor = new LogoutInterceptor(logouts);
        registry.addInterceptor(logoutInterceptor)
                .addPathPatterns(SESSION_LOGOUT_URI + "/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public abstract static class TestAbstractOAuthSessionAuthentication extends OAuthSessionAuthentication {
        public static final String AUTHORIZATION_CODE = "authorizationCode";
        private final AuthProperties authProperties;

        public TestAbstractOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate, AuthProperties authProperties) {
            super(memberService, restTemplate);
            this.authProperties = authProperties;
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
        public TestNaverOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate, AuthProperties authProperties) {
            super(memberService, restTemplate, authProperties);
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
        public TestGithubOAuthSessionAuthentication(MemberService memberService, RestTemplate restTemplate, AuthProperties authProperties) {
            super(memberService, restTemplate, authProperties);
        }

        public boolean isSupported(HttpServletRequest request) {
            String loginUri = SESSION_LOGIN_URI + "/" + SocialType.GITHUB.getServiceName();
            return loginUri.equals(request.getRequestURI());
        }

        @Override
        protected OAuthApiResponse requestUserInfo(String accessToken) {
            return new GithubOAuthApiResponse("monkeyDugi@gmail.com");
        }
    }
}
