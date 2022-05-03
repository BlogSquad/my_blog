package project.myblog.acceptance.comment;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

public class CommentStepsAssert {
    public static void 댓글_작성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 댓글_목록_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.comments.contents")).containsExactly("댓글1", "댓글2");
        assertThat(response.jsonPath().getList("data.comments.author")).containsExactly(NAVER_EMAIL, NAVER_EMAIL);
    }

    public static void 댓글_수정_or_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 댓글_작성_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void 존재하지_않는_댓글(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static void 타인_댓글_수정_or_삭제_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    public static void 대댓글_목록_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.comments.contents")).containsExactly("댓글1");
        assertThat(response.jsonPath().getList("data.comments.author")).containsExactly(NAVER_EMAIL);
        assertThat(response.jsonPath().getList("data.comments.children[0].contents")).containsExactly("대댓글1", "대댓글2", "대대댓글1");
        assertThat(response.jsonPath().getList("data.comments.children[0].author")).containsExactly(NAVER_EMAIL, NAVER_EMAIL, NAVER_EMAIL);

//        assertThat(response.jsonPath().getList("data.comments.children[0].contents")).containsExactly("대댓글1", "대대댓글1");
        assertThat(response.jsonPath().getList("data.comments.children[0].parentId")).containsExactly(1, 1, 1);
        assertThat(response.jsonPath().getList("data.comments.children[0].commentId")).containsExactly(2, 3, 4);
    }
}
