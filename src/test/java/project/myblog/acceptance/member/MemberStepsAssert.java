package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;

public class MemberStepsAssert {
    public static String 로그인_요청_로그인_됨(String serviceName) {
        ExtractableResponse<Response> loginResponse = 로그인_요청(given(), AUTHORIZATION_CODE, serviceName);
        로그인_됨(loginResponse);
        return loginResponse.sessionId();
    }

    public static void 내_회원_정보_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("email")).isEqualTo("monkeyDugi@gmail.com");
        assertThat(response.jsonPath().getString("introduction")).isEqualTo("한줄 소개가 작성되지 않았습니다.");
        assertThat(response.jsonPath().getString("subject")).isEqualTo("monkeyDugi");
    }

    public static void 내_회원_정보_조회_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.sessionId()).isNullOrEmpty();
    }

    public static void 내_회원_정보_수정_됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
