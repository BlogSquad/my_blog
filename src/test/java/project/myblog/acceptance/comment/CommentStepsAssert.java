package project.myblog.acceptance.comment;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

public class CommentStepsAssert {
    public static void 댓글_작성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }

    public static void 댓글_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("contents")).containsExactly("댓글1", "댓글2");
        assertThat(response.jsonPath().getList("author")).containsExactly(NAVER_EMAIL, NAVER_EMAIL);
    }

    public static void 댓글_작성_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
