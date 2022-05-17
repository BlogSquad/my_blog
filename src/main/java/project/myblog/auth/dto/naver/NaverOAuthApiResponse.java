package project.myblog.auth.dto.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import project.myblog.auth.dto.OAuthApiResponse;

public class NaverOAuthApiResponse implements OAuthApiResponse {
    @JsonProperty("response")
    private Response response;

    public NaverOAuthApiResponse() {
    }

    public NaverOAuthApiResponse(Response response) {
        this.response = response;
    }

    public String getEmail() {
        return response.getEmail();
    }

    public static class Response {
        private String email;

        public Response() {
        }

        public Response(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }
}
