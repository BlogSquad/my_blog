package project.myblog.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.comments.CommentsStepRequest.댓글_작성_요청;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_되어있음;
import static project.myblog.auth.dto.SocialType.NAVER;

class CommentsDocumentation extends Documentation {
    @Test
    void 포스트_작성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_되어있음(sessionId, "포스트1제목", "포스트1내용");

        // when
        댓글_작성_요청(
                givenRestDocsFieldDescriptorRequestFields("comments-create", getFieldDescriptorsRequest())
                , sessionId, "댓글1"
        );
    }

    private FieldDescriptor[] getFieldDescriptorsRequest() {
        return new FieldDescriptor[] {
                fieldWithPath("contents").description("댓글 내용")
        };
    }
}
