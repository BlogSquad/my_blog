package project.myblog.acceptance.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_작성됨;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_조회됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_되어있음;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_조회_요청;
import static project.myblog.auth.dto.SocialType.NAVER;

@DisplayName("내 포스트 관리")
class PostAcceptance extends AcceptanceTest {
    /**
     * Given 로그인 되어 있음
     * When 포스트 작성 요청
     * Then 포스트 작성됨
     */
    @Test
    void 포스트_작성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        String title = "포스트1제목";
        String contents = "포스트1내용";

        // when
        ExtractableResponse<Response> response = 포스트_작성_요청(given(), sessionId, title, contents);

        // then
        포스트_작성됨(response);
    }

    /**
     * Given 포스트가 작성되어 있음
     * When 포스트 조회 요청
     * Then 포스트가 조회됨
     */
    @Test
    void 포스트_조회() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        ExtractableResponse<Response> createResponse = 포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        // when
        ExtractableResponse<Response> response = 포스트_조회_요청(given(), createResponse);

        // then
        포스트_조회됨(response);
    }
}
