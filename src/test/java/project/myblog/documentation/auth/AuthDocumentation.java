package project.myblog.documentation.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import project.myblog.documentation.Documentation;

import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;
import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;

class AuthDocumentation extends Documentation {
    @Test
    void 세션_로그인_네이버() {
        로그인_요청(AUTHORIZATION_CODE, NAVER.getServiceName());
    }

    private ExtractableResponse<Response> 로그인_요청(String authorizationCode, String serviceName) {
        return givenRestDocs("auth")
                .queryParam("code", authorizationCode)
                .when().get(SESSION_LOGIN_URI + "/" + serviceName)
                .then().log().all().extract();
    }
}
