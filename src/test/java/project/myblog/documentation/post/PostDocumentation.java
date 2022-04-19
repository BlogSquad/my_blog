package project.myblog.documentation.post;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import project.myblog.documentation.Documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_요청;
import static project.myblog.auth.dto.SocialType.NAVER;

class PostDocumentation extends Documentation {
    @Test
    void 포스트_작성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        // when
        포스트_작성_요청(
                givenRestDocsFieldDescriptorRequestFields("post-create", getFieldDescriptorsRequest())
                , sessionId, "첫 포스팅", "첫 포스팅을 작성했다."
        );
    }

    private FieldDescriptor[] getFieldDescriptorsRequest() {
        return new FieldDescriptor[] {
                fieldWithPath("title").description("포스트 제목"),
                fieldWithPath("contents").description("포스트 내용")
        };
    }
}
