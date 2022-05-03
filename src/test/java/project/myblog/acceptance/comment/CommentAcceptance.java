package project.myblog.acceptance.comment;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import project.myblog.acceptance.AcceptanceTest;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.comment.CommentStepsAssert.대댓글_목록_조회됨;
import static project.myblog.acceptance.comment.CommentStepsAssert.댓글_목록_조회됨;
import static project.myblog.acceptance.comment.CommentStepsAssert.댓글_수정_or_삭제됨;
import static project.myblog.acceptance.comment.CommentStepsAssert.댓글_작성_안됨;
import static project.myblog.acceptance.comment.CommentStepsAssert.댓글_작성됨;
import static project.myblog.acceptance.comment.CommentStepsAssert.존재하지_않는_댓글;
import static project.myblog.acceptance.comment.CommentStepsAssert.타인_댓글_수정_or_삭제_안됨;
import static project.myblog.acceptance.comment.CommentStepsRequest.대대댓글_작성_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.대댓글_작성_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_목록_조회_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_삭제_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_수정_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_작성_요청;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_되어있음;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;

@DisplayName("댓글 관리")
class CommentAcceptance extends AcceptanceTest {
    /**
     * Given 로그인 되어 있음
     * And 포스트가 작성되어 있음
     * When 댓글 작성 요청
     * Then 댓글 작성됨
     * Given 댓글 작성 요청
     * When 댓글 조회 요청
     * Then 댓글 조회됨
     * When 댓글 수정 요청
     * Then 댓글 수정됨
     * When 댓글 삭제 요청
     * Then 댓글 삭제됨
     */
    @Test
    void 댓글_관리() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");
        ExtractableResponse<Response> createResponse = 댓글_작성_요청(given(), sessionId, "댓글1");
        댓글_작성됨(createResponse);

        댓글_작성_요청(given(), sessionId, "댓글2");
        ExtractableResponse<Response> findResponse = 댓글_목록_조회_요청(given());
        댓글_목록_조회됨(findResponse);

        ExtractableResponse<Response> updateResponse = 댓글_수정_요청(given(), sessionId, "댓글1 수정");
        댓글_수정_or_삭제됨(updateResponse);

        ExtractableResponse<Response> deleteResponse = 댓글_삭제_요청(given(), sessionId);
        댓글_수정_or_삭제됨(deleteResponse);
    }

    /**
     * Given 로그인 되어 있음
     * And 포스트가 작성되어 있음
     * And 댓글 작성되어 있음
     * When 대댓글1 작성 요청
     * And 대댓글2 작성 요청
     * When 대대댓글 작성 요청
     * When 댓글 목록 조회 요청
     * Then 댓글, 대댓글, 대대댓글 조회됨.
     */
    @DisplayName("대대댓글 작성 시 댓글에 대댓글로 작성된다.")
    @Test
    void 대댓글_관리() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");
        댓글_작성_요청(given(), sessionId, "댓글1");

        대댓글_작성_요청(given(), sessionId, "대댓글1");
        대댓글_작성_요청(given(), sessionId, "대댓글2");

        ExtractableResponse<Response> response = 대대댓글_작성_요청(given(), sessionId, "대대댓글1");
        댓글_작성됨(response);

        ExtractableResponse<Response> findResponse = 댓글_목록_조회_요청(given());
        대댓글_목록_조회됨(findResponse);
    }

    /**
     * Given 로그인 되어 있음
     * And 포스트가 작성되어 있음
     * When 댓글 수정 요청
     * Then 댓글 수정 안됨
     */
    @Test
    void 예외_존재하지_않는_댓글_수정() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        ExtractableResponse<Response> response = 댓글_수정_요청(given(), sessionId, "댓글1 수정");

        존재하지_않는_댓글(response);
    }

    /**
     * Given 회원1 로그인 되어 있음
     * And 회원2 로그인 되어 있음
     * And 회원2 포스트 작성 되어있음
     * And 회원2 댓글 작성 되어있음
     * When 회원1이 회원2 댓글 수정 요청
     * Then 댓글 수정 안됨
     */
    @Test
    void 예외_타인의_댓글_수정() {
        // given
        String sessionId1 = 로그인_요청_로그인_됨(NAVER.getServiceName());
        String sessionId2 = 로그인_요청_로그인_됨(GITHUB.getServiceName());
        포스트_작성_되어있음(sessionId2, "포스트1제목", "포스트1내용");
        댓글_작성_요청(given(), sessionId2, "댓글1");

        ExtractableResponse<Response> response = 댓글_수정_요청(given(), sessionId1, "댓글1 수정");

        타인_댓글_수정_or_삭제_안됨(response);
    }

    /**
     * Given 로그인 되어 있음
     * And 포스트가 작성되어 있음
     * When 댓글 삭제 요청
     * Then 댓글 삭제 안됨
     */
    @Test
    void 예외_존재하지_않는_댓글_삭제() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        ExtractableResponse<Response> response = 댓글_삭제_요청(given(), sessionId);

        존재하지_않는_댓글(response);
    }

    /**
     * Given 회원1 로그인 되어 있음
     * And 회원2 로그인 되어 있음
     * And 회원2 포스트 작성 되어있음
     * And 회원2 댓글 작성 되어있음
     * When 회원1이 회원2 댓글 삭제 요청
     * Then 댓글 삭제 안됨
     */
    @Test
    void 예외_타인의_댓글_삭제() {
        // given
        String sessionId1 = 로그인_요청_로그인_됨(NAVER.getServiceName());
        String sessionId2 = 로그인_요청_로그인_됨(GITHUB.getServiceName());
        포스트_작성_되어있음(sessionId2, "포스트1제목", "포스트1내용");
        댓글_작성_요청(given(), sessionId2, "댓글1");

        ExtractableResponse<Response> response = 댓글_삭제_요청(given(), sessionId1);

        타인_댓글_수정_or_삭제_안됨(response);
    }

    /**
     * Given 로그인 되어 있음
     * And 포스트가 작성되어 있음
     * When 댓글 작성 요청
     * Then 댓글 작성 안됨
     */
    @DisplayName("NULL, Empty, 공백 검증")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void 예외_댓글_작성_유효성_검증(String contents) {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        ExtractableResponse<Response> response = 댓글_작성_요청(given(), sessionId, contents);

        댓글_작성_안됨(response);
    }
}
