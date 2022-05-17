package project.myblog.auth.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import project.myblog.auth.dto.OAuthApiResponse;

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
