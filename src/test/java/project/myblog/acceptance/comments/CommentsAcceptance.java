package project.myblog.acceptance.comments;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.comments.CommentsStepRequest.댓글_작성_요청;
import static project.myblog.acceptance.comments.CommentsStepsAssert.댓글_작성됨;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_되어있음;
import static project.myblog.auth.dto.SocialType.NAVER;

@DisplayName("댓글 관리")
class CommentsAcceptance extends AcceptanceTest {
    /**
     * Given 로그인 되어 있음
     * And 포스트가 작성되어 있음
     * When 댓글 작성 요청
     * Then 댓글 작성됨
     */
    @Test
    void 댓글_생성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        // when
        ExtractableResponse<Response> response = 댓글_작성_요청(given(), sessionId, "댓글1");

        // then
        댓글_작성됨(response);
    }
}
