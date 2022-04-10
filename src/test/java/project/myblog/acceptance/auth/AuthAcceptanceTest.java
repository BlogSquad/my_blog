package project.myblog.acceptance.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static project.myblog.acceptance.auth.AuthSteps.내_회원_정보_조회_안됨;
import static project.myblog.acceptance.auth.AuthSteps.내_회원_정보_조회_요청;
import static project.myblog.acceptance.auth.AuthSteps.내_회원_정보_조회됨;
import static project.myblog.acceptance.auth.AuthSteps.로그아웃_요청;
import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_안됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;
import static project.myblog.web.dto.SocialType.GITHUB;
import static project.myblog.web.dto.SocialType.NAVER;

@DisplayName("로그인 인증, 인가 관리")
class AuthAcceptanceTest extends AcceptanceTest {
    /**
     * When 네이버 로그인 요청
     * Then 로그인 안됨
     */
    @Test
    void 네이버_세션_로그인_존재하지_않는_인증_코드() {
        ExtractableResponse<Response> response = 로그인_요청("notExistsAuthorizationCode", NAVER.getServiceName());

        로그인_안됨(response);
    }

    /**
     * When 깃허브 로그인 요청
     * Then 로그인 안됨
     */
    @Test
    void 깃허브_세션_로그인_존재하지_않는_인증_코드() {
        ExtractableResponse<Response> response = 로그인_요청("notExistsAuthorizationCode", GITHUB.getServiceName());

        로그인_안됨(response);
    }

    /**
     * When 네이버 로그인 요청
     * Then 로그인 안됨
     * When 내 회원 정보 요청
     * Then 회원 정보 조회됨
     * When 로그아웃 요청
     * When 로그인 없이 내 회원 정보 요청
     * Then 회원 정보 조회 안됨
     */
    @Test
    void 네이버_세션_내_정보_조회_관리() {
        ExtractableResponse<Response> loginResponse = 로그인_요청(AUTHORIZATION_CODE, NAVER.getServiceName());
        String sessionId = loginResponse.sessionId();
        로그인_됨(loginResponse);

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(sessionId);
        내_회원_정보_조회됨(membersMeResponse);

        로그아웃_요청(sessionId);

        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(sessionId);
        내_회원_정보_조회_안됨(response);
    }

    /**
     * When 깃허브 로그인 요청
     * Then 로그인 안됨
     * When 내 회원 정보 요청
     * Then 회원 정보 조회됨
     * When 로그아웃 요청
     * When 로그인 없이 내 회원 정보 요청
     * Then 회원 정보 조회 안됨
     */
    @Test
    void 깃허브_세션_내_정보_조회_관리() {
        ExtractableResponse<Response> loginResponse = 로그인_요청(AUTHORIZATION_CODE, GITHUB.getServiceName());
        String sessionId = loginResponse.sessionId();
        로그인_됨(loginResponse);

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(sessionId);
        내_회원_정보_조회됨(membersMeResponse);

        로그아웃_요청(sessionId);

        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(sessionId);
        내_회원_정보_조회_안됨(response);
    }
}
