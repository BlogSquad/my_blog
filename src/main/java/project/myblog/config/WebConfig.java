package project.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.auth.authentication.Logout;
import project.myblog.auth.authentication.OAuthAuthentication;
import project.myblog.auth.authentication.intercpetor.LogoutInterceptor;
import project.myblog.auth.authentication.intercpetor.OAuthAuthenticationInterceptor;
import project.myblog.auth.authentication.session.SessionLogout;
import project.myblog.auth.authentication.session.github.GithubOAuthSessionAuthentication;
import project.myblog.auth.authentication.session.naver.NaverOAuthSessionAuthentication;
import project.myblog.auth.authorization.Authorization;
import project.myblog.auth.authorization.interceptor.AuthorizationInterceptor;
import project.myblog.auth.authorization.session.SessionAuthorization;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.LoginMemberArgumentResolver;
import project.myblog.service.AuthService;

import java.util.ArrayList;
import java.util.List;

@Configuration(value = "webConfig")
public class WebConfig implements WebMvcConfigurer {
    public static String SESSION_LOGIN_URI = "/login/session";
    public static String SESSION_LOGOUT_URI = "/logout/session";

    private final AuthService authService;
    private final AuthProperties authProperties;

    public WebConfig(AuthService authService, AuthProperties authProperties) {
        this.authService = authService;
        this.authProperties = authProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<OAuthAuthentication> oAuthAuthentications = new ArrayList<>();
        oAuthAuthentications.add(new NaverOAuthSessionAuthentication(authService, restTemplate(), authProperties));
        oAuthAuthentications.add(new GithubOAuthSessionAuthentication(authService, restTemplate(), authProperties));

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
}
