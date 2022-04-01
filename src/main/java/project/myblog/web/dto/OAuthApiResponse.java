package project.myblog.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthApiResponse {
    @JsonProperty("response")
    private final Response response;

    public String getEmail() {
        return response.getEmail();
    }

    public OAuthApiResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        private final String email;
        public String getEmail() {
            return email;
        }

        public Response(String email) {
            this.email = email;
        }
    }
}
