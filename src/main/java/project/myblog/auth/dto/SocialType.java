package project.myblog.auth.dto;

public enum SocialType {
    NAVER(
            "naver",
            "https://nid.naver.com/oauth2.0/token",
            "authorization_code",
            "https://openapi.naver.com/v1/nid/me"
    ),
    GITHUB(
            "github",
            "https://github.com/login/oauth/access_token",
            "",
            "https://api.github.com/user/emails"
    );

    private final String serviceName;
    private final String accessTokenUri;
    private final String grantType;
    private final String apiMeUri;

    SocialType(String serviceName, String accessTokenUri, String grantType, String apiMeUri) {
        this.serviceName = serviceName;
        this.accessTokenUri = accessTokenUri;
        this.grantType = grantType;
        this.apiMeUri = apiMeUri;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getApiMeUri() {
        return apiMeUri;
    }
}
