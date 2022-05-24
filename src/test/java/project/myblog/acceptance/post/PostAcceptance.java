package project.myblog.acceptance.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsAssert.존재하지_않는_포스트;
import static project.myblog.acceptance.post.PostStepsAssert.타인_포스트_수정_or_삭제_안됨;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_목록_조회됨;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_수정_or_삭제됨;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_작성됨;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_조회됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_목록_조회_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_삭제_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_수정_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_되어있음;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_조회_요청;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;

@DisplayName("포스트 관리")
class PostAcceptance extends AcceptanceTest {
    /**
     * Given 로그인 되어 있음
     * When 포스트 작성 요청
     * Then 포스트 작성됨
     * When 포스트 조회 요청
     * Then 포스트 조회됨
     * When 포스트 수정 요청
     * Then 포스트 수정됨
     * When 포스트 삭제 요청
     * Then 포스트 삭제됨
     */
    @Test
    void 포스트_관리() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        ExtractableResponse<Response> createResponse = 포스트_작성_요청(given(), sessionId, "포스트1제목", "포스트1내용");
        포스트_작성됨(createResponse);

        ExtractableResponse<Response> findResponse = 포스트_조회_요청(given());
        포스트_조회됨(findResponse);

        ExtractableResponse<Response> updateResponse = 포스트_수정_요청(given(), sessionId, "포스트1제목 변경", "포스트1내용 변경");
        포스트_수정_or_삭제됨(updateResponse);

        ExtractableResponse<Response> deleteResponse = 포스트_삭제_요청(given(), sessionId);
        포스트_수정_or_삭제됨(deleteResponse);
    }

    /**
     * Given 로그인 되어 있음
     * And 포스트 작성 요청
     * And 포스트 작성 요청
     * When 포스트 목록 조회
     * then 요청한 페이지의 포스트 목록이 조회된다.
     */
    @DisplayName("페이징 기반으로 조회힌다.")
    @Test
    void 포스트_목록_조회() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_요청(given(), sessionId, "포스트1제목", "포스트1내용");
        포스트_작성_요청(given(), sessionId, "포스트2제목", "포스트2내용");

        int page = 0;
        ExtractableResponse<Response> response = 포스트_목록_조회_요청(given(), page);

        포스트_목록_조회됨(response);
    }

    /**
     * When 포스트 조회 요청
     * Then 포스트가 조회 안됨
     */
    @Test
    void 예외_존재하지_않는_포스트_조회() {
        ExtractableResponse<Response> response = 포스트_조회_요청(given());

        존재하지_않는_포스트(response);
    }

    /**
     * Given 로그인 되어 있음
     * When 포스트 수정 요청
     * Then 포스트가 수정 안됨
     */
    @Test
    void 예외_존재하지_않는_포스트_수정() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        ExtractableResponse<Response> response = 포스트_수정_요청(given(), sessionId,
                "포스트2제목 변경", "포스트2내용 변경");

        존재하지_않는_포스트(response);
    }

    /**
     * Given 로그인 되어 있음
     * When 포스트 삭제 요청
     * Then 포스트가 삭제 안됨
     */
    @Test
    void 예외_존재하지_않는_포스트_삭제() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        ExtractableResponse<Response> response = 포스트_삭제_요청(given(), sessionId);

        존재하지_않는_포스트(response);
    }

    /**
     * Given 회원1 로그인 되어있음
     * And 회원2 로그인 되어 있음
     * And 회원2 포스트 작성 되어있음
     * When 회원1이 회원2 포스트 수정 요청
     * Then 포스트가 수정 안됨
     */
    @Test
    void 예외_타인의_포스트_수정() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        String sessionId2 = 로그인_요청_로그인_됨(GITHUB.getServiceName());
        포스트_작성_되어있음(sessionId2, "포스트2제목", "포스트2내용");

        ExtractableResponse<Response> response = 포스트_수정_요청(given(), sessionId,
                                                        "포스트2제목 변경", "포스트2내용 변경");

        타인_포스트_수정_or_삭제_안됨(response);
    }

    /**
     * Given 회원1 로그인 되어있음
     * And 회원2 로그인 되어 있음
     * And 회원2 포스트 작성 되어있음
     * When 회원1이 회원2 포스트 삭제 요청
     * Then 포스트가 삭제 안됨
     */
    @Test
    void 예외_타인의_포스트_삭제() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        String sessionId2 = 로그인_요청_로그인_됨(GITHUB.getServiceName());
        포스트_작성_되어있음(sessionId2, "포스트2제목", "포스트2내용");

        ExtractableResponse<Response> response = 포스트_삭제_요청(given(), sessionId);

        타인_포스트_수정_or_삭제_안됨(response);
    }
}
