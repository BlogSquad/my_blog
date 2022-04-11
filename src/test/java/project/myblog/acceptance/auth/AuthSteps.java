package project.myblog.acceptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;
import static project.myblog.config.WebConfig.SESSION_LOGOUT_URI;

public class AuthSteps {
    public static ExtractableResponse<Response> 로그인_요청(String authorizationCode, String serviceName) {
        return RestAssured
                .given().log().all()
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

    public static ExtractableResponse<Response> 내_회원_정보_조회_요청(String sessionId) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .when().get("members/me")
                .then().log().all().extract();
    }

    public static void 내_회원_정보_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("email")).isEqualTo("monkeyDugi@gmail.com");
    }

    public static void 내_회원_정보_조회_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.sessionId()).isNullOrEmpty();
    }
}
