package project.myblog.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthProperties {
    @Value("${oauth.naver.client_id}")
    private String clientId;
    @Value("${oauth.naver.client_secret_id}")
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
