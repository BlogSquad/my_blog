package project.myblog.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static project.myblog.acceptance.comment.CommentStepsRequest.대댓글_작성_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_삭제_요청;
import static project.myblog.acceptance.comment.CommentStepsRequest.댓글_수정_요청;
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
                givenRestDocsFieldDescriptorRequestFieldsAndPathParam("comment-create",
                        getFieldDescriptorsRequest("contents", "댓글 내용"),
                        getPathParameters("postId", "포스트 ID")
                )
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
        댓글_조회_요청(
                givenRestDocsFieldDescriptorRelaxedResponseFieldsAndPathParam("comment-find",
                        getFieldDescriptorsResponse(),
                        getPathParameters("postId", "포스트 ID")
                )
        );
    }

    @Test
    void 댓글_수정() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");
        댓글_작성_요청(given(), sessionId, "댓글1");

        // when
        댓글_수정_요청(
                givenRestDocsFieldDescriptorRequestFieldsAndPathParam("comment-update",
                        getFieldDescriptorsRequest("contents", "댓글 내용"),
                        getPathParameters("commentId", "댓글 ID")
                )
                , sessionId, "댓글1 수정"
        );
    }

    @Test
    void 댓글_삭제() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");
        댓글_작성_요청(given(), sessionId, "댓글1");

        // when
        댓글_삭제_요청(
                givenRestDocsPathParam("comment-delete",
                        getPathParameters("commentId", "댓글 ID"))
                , sessionId
        );
    }

    @Test
    void 대댓글_작성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        댓글_작성_요청(given(), sessionId, "댓글1");

        // when
        대댓글_작성_요청(
                givenRestDocsFieldDescriptorRequestFieldsAndPathParam("nestedComment-create",
                        getFieldDescriptorsRequest("contents", "대댓글 내용"),
                        getPathParametersNestedComment()
                )
                , sessionId, "대댓글1"
        );
    }

    private FieldDescriptor[] getFieldDescriptorsRequest(String name, String description) {
        return new FieldDescriptor[] {
                fieldWithPath(name).description(description)
        };
    }

    private FieldDescriptor[] getFieldDescriptorsResponse() {
        return new FieldDescriptor[] {
                fieldWithPath("[].contents").description("댓글 내용"),
                fieldWithPath("[].author").description("작성자")
        };
    }

    private ParameterDescriptor[] getPathParameters(String name, String description) {
        return new ParameterDescriptor[] {
                parameterWithName(name).description(description)
        };
    }

    private ParameterDescriptor[] getPathParametersNestedComment() {
        return new ParameterDescriptor[] {
                parameterWithName("postId").description("포스트 ID"),
                parameterWithName("parentId").description("상위 댓글 ID")
        };
    }
}
