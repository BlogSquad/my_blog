package project.myblog.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.authentication.LoginMemberArgumentResolver;
import project.myblog.authentication.OAuthLogin;
import project.myblog.authentication.OAuthLoginInterceptor;
import project.myblog.authentication.session.OAuthSessionLogin;
import project.myblog.authorization.AuthorizationLoginInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;
import project.myblog.web.dto.OAuthApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@TestConfiguration(value = "webConfig")
public class TestWebConfig implements WebMvcConfigurer {
    private final AuthService authService;
    private final AuthProperties authProperties;

    public TestWebConfig(AuthService authService, AuthProperties authProperties) {
        this.authService = authService;
        this.authProperties = authProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<OAuthLogin> oAuthLogins = new ArrayList<>();
        oAuthLogins.add(new TestNaverOAuthSessionLogin(authService, restTemplate(), authProperties));

        OAuthLoginInterceptor oAuthLoginInterceptor = new OAuthLoginInterceptor(oAuthLogins);

        registry.addInterceptor(oAuthLoginInterceptor)
                .addPathPatterns("/login/**");
        registry.addInterceptor(new AuthorizationLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css", "/logout/**", "/login/**",
                        "/docs/**", "/favicon.ico", "/api/error", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static class TestNaverOAuthSessionLogin extends OAuthSessionLogin {
        public static final String AUTHORIZATION_CODE = "authorizationCode";
        private final AuthProperties authProperties;

        public TestNaverOAuthSessionLogin(AuthService authService, RestTemplate restTemplate, AuthProperties authProperties) {
            super(authService, restTemplate);
            this.authProperties = authProperties;
        }

        public boolean isSupported(HttpServletRequest request) {
            String uri = request.getRequestURI();
            return uri.contains("naver");
        }

        @Override
        protected String requestAccessToken(HttpServletRequest request) {
            String code = request.getParameter("code");
            if (AUTHORIZATION_CODE.equals(code)) {
                return "testAccessToken";
            }
            return null;
        }

        @Override
        protected OAuthApiResponse requestUserInfo(String accessToken) {
            return new OAuthApiResponse(new OAuthApiResponse.Response("monkeyDugi@gmail.com"));
        }
    }
}
