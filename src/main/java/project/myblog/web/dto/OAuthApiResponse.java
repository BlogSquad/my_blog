package project.myblog.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthApiResponse {
    @JsonProperty("response")
    private Response response;

    public OAuthApiResponse() {
    }

    public OAuthApiResponse(Response response) {
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
