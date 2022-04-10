package project.myblog.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubOAuthApiResponse implements OAuthApiResponse {
    @JsonProperty("email")
    private String email;

    public GithubOAuthApiResponse() {
    }

    public GithubOAuthApiResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
