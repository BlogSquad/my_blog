package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.acceptance.member.MemberSteps.내_회원_정보_조회_요청;
import static project.myblog.acceptance.member.MemberSteps.내_회원_정보_조회됨;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;

@DisplayName("회원 정보 관리")
class MemberAcceptance extends AcceptanceTest {
    /**
     * Given 네이버 로그인 됨
     * When 내 회원 정보 요청
     * Then 회원 정보 조회됨
     */
    @Test
    void 네이버_세션_내_정보_조회() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(sessionId);

        내_회원_정보_조회됨(membersMeResponse);
    }

    /**
     * Given 깃허브 로그인 됨
     * When 내 회원 정보 요청
     * Then 회원 정보 조회됨
     */
    @Test
    void 깃허브_세션_내_정보_조회() {
        String sessionId = 로그인_요청_로그인_됨(GITHUB.getServiceName());

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(sessionId);

        내_회원_정보_조회됨(membersMeResponse);
    }

    private String 로그인_요청_로그인_됨(String serviceName) {
        ExtractableResponse<Response> loginResponse = 로그인_요청(AUTHORIZATION_CODE, serviceName);
        로그인_됨(loginResponse);
        return loginResponse.sessionId();
    }
}
