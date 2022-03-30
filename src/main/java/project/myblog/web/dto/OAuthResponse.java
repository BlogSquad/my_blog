package project.myblog.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthResponse {
    @JsonProperty("response")
    private Response response;

    public String getEmail() {
        return response.getEmail();
    }

    private static class Response {
        private String id;
        private String email;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }
    }
}
