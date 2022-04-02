package project.myblog.acceptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import project.myblog.AcceptanceTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("로그인 인증 관리")
class AuthAcceptanceTest extends AcceptanceTest {

    /**
     * When 네이버 로그인 요청
     * then 로그인됨
     */
    @Test
    void 세션_로그인_네이버() {
        ExtractableResponse<Response> response = 로그인_요청();

        로그인됨(response);
    }

    /**
     * Given 네이버 로그인 요청
     * When 내 회원 정보 요청
     * Then 회원 정보 조회됨
     */
    @Test
    void 세션_로그인_후_내_정보_조회_네이버() {
        // given
        String sessionId = 로그인_요청().sessionId();

        // when
        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(sessionId);

        // then
        내_회원_정보_조회됨(response);
    }

    /**
     * When 로그인 없이 내 회원 정보 요청
     * Then 회원 정보 조회 안됨
     */
//    @Test
//    void 로그인_없이_회원_정보_요청() {
//        // when
//        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(null);
//
//        // then
//        내_회원_정보_조회_안됨(response);
//    }

    public static ExtractableResponse<Response> 로그인_요청() {
        return RestAssured
                .given().log().all()
                .queryParam("code", "testAuthorizationCode")
                .when().get("/login/oauth2/code/naver")
                .then().log().all().extract();
    }

    private void 로그인됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.sessionId()).isNotEmpty();
    }

    private ExtractableResponse<Response> 내_회원_정보_조회_요청(String sessionId) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .when().get("members/me")
                .then().log().all().extract();
    }

    private void 내_회원_정보_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("email")).isEqualTo("monkeyDugi@gmail.com");
    }
}
