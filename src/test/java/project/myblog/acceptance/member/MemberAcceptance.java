package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import project.myblog.acceptance.AcceptanceTest;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.member.MemberStepsAssert.내_회원_정보_수정_됨;
import static project.myblog.acceptance.member.MemberStepsAssert.내_회원_정보_수정_안됨;
import static project.myblog.acceptance.member.MemberStepsAssert.내_회원_정보_조회_안됨_존재하지_않는_회원;
import static project.myblog.acceptance.member.MemberStepsAssert.내_회원_정보_조회됨;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.member.MemberStepsAssert.회원_탈퇴_됨;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청_제목;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청_한줄_소개;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_조회_요청;
import static project.myblog.acceptance.member.MemberStepsRequest.회원_탈퇴_요청;
import static project.myblog.auth.dto.SocialType.NAVER;

@DisplayName("회원 정보 관리")
class MemberAcceptance extends AcceptanceTest {
    /**
     * Given 로그인 됨
     * When 내 회원 한줄 소개 수정 요청
     * Then 회원 정보 수정됨
     * When 내 회원 제목 수정 요청
     * Then 회원 정보 수정됨
     * When 내_회원_정보_조회_요청
     * Then 회원 정보 조회됨
     */
    @Test
    void 내_회원_정보_관리() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        ExtractableResponse<Response> responseIntroduction = 내_회원_정보_수정_요청_한줄_소개(given(), sessionId, "한줄 소개 변경");
        내_회원_정보_수정_됨(responseIntroduction);

        ExtractableResponse<Response> responseSubject = 내_회원_정보_수정_요청_제목(given(), sessionId, "제목 변경");
        내_회원_정보_수정_됨(responseSubject);

        ExtractableResponse<Response> membersMeResponse = 내_회원_정보_조회_요청(given(), sessionId);
        내_회원_정보_조회됨(membersMeResponse, "한줄 소개 변경", "제목 변경");
    }

    /**
     * Given 로그인 되어 있음
     * When 회원 탈퇴 요청
     * Then 회원 탈퇴됨
     */
    @Test
    void 회원_탈퇴() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        ExtractableResponse<Response> response = 회원_탈퇴_요청(given(), sessionId);

        회원_탈퇴_됨(response);
    }

    /**
     * Given 로그인 되어 있음
     * And 회원 탈퇴 요청
     * When 내_회원_정보_조회_요청
     * Then 회원 정보 조회 안됨
     */
    @Test
    void 예외_존재하지_않는_회원() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        회원_탈퇴_요청(given(), sessionId);

        ExtractableResponse<Response> responseIntroduction = 내_회원_정보_조회_요청(given(), sessionId);

        내_회원_정보_조회_안됨_존재하지_않는_회원(responseIntroduction);
    }

    /**
     * Given 로그인 됨
     * When 내 회원 한 줄 소개 수정 요청
     * Then 회원 정보 수정 안됨
     */
    @DisplayName("NULL, Empty, 공백 검증")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void 예외_내_회원_정보_수정_한줄_소개_유효성_검증(String introduction) {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        ExtractableResponse<Response> response = 내_회원_정보_수정_요청_한줄_소개(given(), sessionId, introduction);

        내_회원_정보_수정_안됨(response);
    }

    /**
     * Given 로그인 됨
     * When 내 회원 제목 수정 요청
     * Then 회원 정보 수정 안됨
     */
    @DisplayName("NULL, Empty, 공백 검증")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void 예외_내_회원_정보_수정_제목_유효성_검증(String introduction) {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        ExtractableResponse<Response> response = 내_회원_정보_수정_요청_제목(given(), sessionId, introduction);

        내_회원_정보_수정_안됨(response);
    }
}
