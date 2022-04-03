package project.myblog.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.authentication.LoginMemberArgumentResolver;
import project.myblog.authentication.TestSessionLoginInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;

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
        registry.addInterceptor(new TestSessionLoginInterceptor(restTemplate(), authService, authProperties))
                .addPathPatterns("/**", "/login/oauth2/code/naver")
                .excludePathPatterns("/", "/css", "/logout/**", "/docs/**", "/favicon.ico", "/api/error", "/error");
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