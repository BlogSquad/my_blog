package project.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.authentication.LoginMemberArgumentResolver;
import project.myblog.authentication.OAuthLogin;
import project.myblog.authentication.OAuthLoginInterceptor;
import project.myblog.authentication.session.NaverOAuthSessionLogin;
import project.myblog.authorization.AuthorizationLoginInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;

import java.util.ArrayList;
import java.util.List;

@Configuration(value = "webConfig")
public class WebConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final AuthProperties authProperties;

    public WebConfig(AuthService authService, AuthProperties authProperties) {
        this.authService = authService;
        this.authProperties = authProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<OAuthLogin> oAuthLogins = new ArrayList<>();
        oAuthLogins.add(new NaverOAuthSessionLogin(authService, restTemplate(), authProperties));

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
}
