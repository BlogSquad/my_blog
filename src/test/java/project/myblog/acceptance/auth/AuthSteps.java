package project.myblog.acceptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;
import static project.myblog.config.WebConfig.SESSION_LOGOUT_URI;

public class AuthSteps {
    public static ExtractableResponse<Response> 로그인_요청(
            RequestSpecification given, String authorizationCode, String serviceName) {
        return given.log().all()
                .queryParam("code", authorizationCode)
                .when().get(SESSION_LOGIN_URI + "/" + serviceName)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 로그아웃_요청(String sessionId) {
        return RestAssured
                .given().log().all()
                .cookie("JSESSIONID", sessionId)
                .when().get(SESSION_LOGOUT_URI)
                .then().log().all().extract();
    }

    public static void 로그인_됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.sessionId()).isNotEmpty();
    }

    public static void 로그인_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.sessionId()).isNullOrEmpty();
    }
}
