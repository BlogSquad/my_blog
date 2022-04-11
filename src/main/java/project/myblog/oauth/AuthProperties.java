package project.myblog.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthProperties {
    @Value("${oauth.naver.acces_token_uri}")
    private String accessTokenUri;
    @Value("${oauth.naver.grant_type}")
    private String grantType = "authorization_code";
    @Value("${oauth.naver.client_id}")
    private String clientId;
    @Value("${oauth.naver.client_secret_id}")
    private String clientSecret;
    @Value("${oauth.naver.api_me_uri}")
    private String apiMeUri;

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getApiMeUri() {
        return apiMeUri;
    }
}
