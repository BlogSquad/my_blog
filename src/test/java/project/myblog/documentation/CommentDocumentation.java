package project.myblog.documentation;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_작성_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_조회_요청;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_되어있음;
import static project.myblog.auth.dto.SocialType.NAVER;

class CommentDocumentation extends Documentation {
    @Test
    void 댓글_작성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        // when
        댓글_작성_요청(
                givenRestDocsFieldDescriptorRequestFields("comment-create", getFieldDescriptorsRequest())
                , sessionId, "댓글1"
        );
    }

    @Test
    void 댓글_조회() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");
        댓글_작성_요청(given(), sessionId, "댓글1");
        댓글_작성_요청(given(), sessionId, "댓글2");

        // when
        댓글_조회_요청(givenRestDocsFieldDescriptorRelaxedResponseFieldsAndPathParam("comment-find", getFieldDescriptorsResponse()));
    }

    private FieldDescriptor[] getFieldDescriptorsRequest() {
        return new FieldDescriptor[] {
                fieldWithPath("contents").description("댓글 내용")
        };
    }

    private FieldDescriptor[] getFieldDescriptorsResponse() {
        return new FieldDescriptor[] {
                fieldWithPath("[].contents").description("댓글 내용"),
                fieldWithPath("[].author").description("작성자")
        };
    }
}
