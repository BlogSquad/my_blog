package project.myblog.acceptance.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

public class PostStepsAssert {
    public static void 포스트_작성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }

    public static void 포스트_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("data.title")).isEqualTo("포스트1제목");
        assertThat(response.jsonPath().getString("data.contents")).isEqualTo("포스트1내용");
        assertThat(response.jsonPath().getString("data.author")).isEqualTo(NAVER_EMAIL);
        assertThat(response.jsonPath().getInt("data.hits")).isEqualTo(0);
    }

    public static void 포스트_수정_or_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 타인_포스트_수정_or_삭제_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    public static void 존재하지_않는_포스트(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
