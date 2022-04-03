package project.myblog.acceptance.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static project.myblog.acceptance.auth.AuthSteps.내_회원_정보_조회_안됨;
import static project.myblog.acceptance.auth.AuthSteps.내_회원_정보_조회_요청;
import static project.myblog.acceptance.auth.AuthSteps.내_회원_정보_조회됨;
import static project.myblog.acceptance.auth.AuthSteps.로그아웃_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그아웃_요청;
import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;

@DisplayName("로그인 인증 관리")
class AuthAcceptanceTest extends AcceptanceTest {

    /**
     * When 네이버 로그인 요청
     * then 로그인됨
     */
    @Test
    void 세션_로그인_네이버() {
        ExtractableResponse<Response> response = 로그인_요청();

        로그인_됨(response);
    }

    /**
     * Given 네이버 로그인 요청
     * When 내 회원 정보 요청
     * Then 회원 정보 조회됨
     */
    @Test
    void 세션_로그인_후_내_정보_조회_네이버() {
        String sessionId = 로그인_요청().sessionId();

        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(sessionId);

        내_회원_정보_조회됨(response);
    }

    /**
     * When 로그인 없이 내 회원 정보 요청
     * Then 회원 정보 조회 안됨
     */
    @Test
    void 로그인_없이_회원_정보_요청_네이버() {
        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(null);

        내_회원_정보_조회_안됨(response);
    }

    /**
     * When 네이버 로그아웃 요청
     * then 로그아웃 됨
     */
    @Test
    void 세션_로그아웃_네이버() {
        String sessionId = 로그인_요청().sessionId();

        ExtractableResponse<Response> response = 로그아웃_요청(sessionId);

        로그아웃_됨(response);
    }
}
