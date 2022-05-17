package project.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.myblog.auth.authentication.Authentication;
import project.myblog.auth.authentication.interceptor.AuthenticationInterceptor;
import project.myblog.auth.authentication.interceptor.MvcPatternInterceptor;
import project.myblog.auth.authentication.session.SessionAuthentication;
import project.myblog.auth.dto.AuthProperties;
import project.myblog.auth.dto.LoginMemberArgumentResolver;
import project.myblog.auth.oauthAuthentication.Logout;
import project.myblog.auth.oauthAuthentication.OAuthAuthentication;
import project.myblog.auth.oauthAuthentication.intercpetor.LogoutInterceptor;
import project.myblog.auth.oauthAuthentication.intercpetor.OAuthAuthenticationInterceptor;
import project.myblog.auth.oauthAuthentication.session.SessionLogout;
import project.myblog.auth.oauthAuthentication.session.github.GithubOAuthSessionAuthentication;
import project.myblog.auth.oauthAuthentication.session.naver.NaverOAuthSessionAuthentication;
import project.myblog.service.MemberService;

import java.util.ArrayList;
import java.util.List;

@Configuration(value = "webConfig")
public class WebConfig implements WebMvcConfigurer {
    public static String SESSION_LOGIN_URI = "/login/oauth2/code";
    public static String SESSION_LOGOUT_URI = "/logout/session";

    protected final MemberService memberService;
    protected final AuthProperties authProperties;

    protected WebConfig(MemberService memberService, AuthProperties authProperties) {
        this.memberService = memberService;
        this.authProperties = authProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(oAuthAuthenticationInterceptor())
                .addPathPatterns(SESSION_LOGIN_URI + "/**");

        registry.addInterceptor(authorizationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css", "/logout/**", "/login/**",
                        "/docs/**", "/favicon.ico", "/api/error", "/error");

        registry.addInterceptor(logoutInterceptor())
                .addPathPatterns(SESSION_LOGOUT_URI + "/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    protected HandlerInterceptor oAuthAuthenticationInterceptor() {
        List<OAuthAuthentication> oAuthAuthentications = new ArrayList<>();
        oAuthAuthentications.add(new NaverOAuthSessionAuthentication(memberService, restTemplate(), authProperties));
        oAuthAuthentications.add(new GithubOAuthSessionAuthentication(memberService, restTemplate(), authProperties));

        return new OAuthAuthenticationInterceptor(oAuthAuthentications);
    }

    protected HandlerInterceptor authorizationInterceptor() {
        List<Authentication> authentications = new ArrayList<>();
        authentications.add(new SessionAuthentication());

        return new MvcPatternInterceptor(new AuthenticationInterceptor(authentications))
                .addExcludePattern(HttpMethod.GET, "/posts/**");
    }

    protected HandlerInterceptor logoutInterceptor() {
        List<Logout> logouts = new ArrayList<>();
        logouts.add(new SessionLogout());

        return new LogoutInterceptor(logouts);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
