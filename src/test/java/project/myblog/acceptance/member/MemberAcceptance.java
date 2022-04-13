package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;
import project.myblog.web.dto.MemberRequest;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.member.MemberStepsAssert.내_회원_정보_수정_됨;
import static project.myblog.acceptance.member.MemberStepsAssert.내_회원_정보_조회됨;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_조회_요청;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;

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

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(given(), sessionId);

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

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(given(), sessionId);

        내_회원_정보_조회됨(membersMeResponse);
    }

    /**
     * Given 네이버 로그인 됨
     * When 내 회원 수정 요청
     * Then 회원 정보 수정됨
     *
     *         * 생성 post, 201, Location
     *         * 수정 patch, 204
     *         * 삭제(탈퇴) patch, 204 활성 -> 비활성
     */
    @Test
    void 네이버_세션_내_회원_정보_수정() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        MemberRequest memberRequest = new MemberRequest("한줄 소개 변경", "제목 변경");

        // when
        ExtractableResponse<Response> response = 내_회원_정보_수정_요청(given(), sessionId, memberRequest);

        // then
        내_회원_정보_수정_됨(response);
    }
}
