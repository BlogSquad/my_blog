package project.myblog.auth.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthProperties {
    @Value("${oauth.naver.client_id:test_client_id}")
    private String naverClientId;
    @Value("${oauth.naver.client_secret_id:test_client_secret_id}")
    private String naverClientSecret;

    @Value("${oauth.github.client_id:test_client_id}")
    private String githubClientId;
    @Value("${oauth.github.client_secret_id:test_client_secret_id}")
    private String githubClientSecret;

    public String getNaverClientId() {
        return naverClientId;
    }

    public String getNaverClientSecret() {
        return naverClientSecret;
    }

    public String getGithubClientId() {
        return githubClientId;
    }

    public String getGithubClientSecret() {
        return githubClientSecret;
    }
}
