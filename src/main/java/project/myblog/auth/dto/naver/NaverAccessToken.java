package project.myblog.auth.dto.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaverAccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expires_in;

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "accessToken='" + accessToken + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
