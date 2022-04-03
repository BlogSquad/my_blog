package project.myblog.documentation.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import project.myblog.documentation.Documentation;

class AuthDocumentation extends Documentation {
    @Test
    void 세션_로그인_네이버() {
        로그인_요청();
    }

    public ExtractableResponse<Response> 로그인_요청() {
        return givenRestDocs("auth")
                .queryParam("code", "authorizationCode")
                .when().get("/login/oauth2/code/naver")
                .then().log().all().extract();
    }
}
