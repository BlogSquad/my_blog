package project.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.authentication.LoginMemberArgumentResolver;
import project.myblog.authentication.SessionLoginInterceptor;
import project.myblog.oauth.AuthProperties;
import project.myblog.service.AuthService;

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
        registry.addInterceptor(new SessionLoginInterceptor(restTemplate(), authService, authProperties))
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css", "/logout/**", "/docs/**", "/favicon.ico");
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
