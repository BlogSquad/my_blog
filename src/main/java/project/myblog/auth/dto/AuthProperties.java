package project.myblog.auth.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthProperties {
    @Value("${auth.naver.client_id}")
    private String clientId;
    @Value("${auth.naver.client_secret_id}")
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
