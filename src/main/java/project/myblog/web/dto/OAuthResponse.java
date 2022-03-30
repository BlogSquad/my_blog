package project.myblog.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthResponse {
    @JsonProperty("response")
    private Response response;

    public String getEmail() {
        return response.getEmail();
    }

    public String getName() {
        return response.getName();
    }

    private static class Response {
        private String email;
        private String name;

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }
}
