package project.myblog.acceptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AuthSteps {
    public static ExtractableResponse<Response> 로그인_요청() {
        return RestAssured
                .given().log().all()
                .queryParam("code", "testAuthorizationCode")
                .when().get("/login/oauth2/code/naver")
                .then().log().all().extract();
    }

    public static void 로그인됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.sessionId()).isNotEmpty();
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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
