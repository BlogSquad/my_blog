package project.myblog.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_삭제_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_수정_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_작성_요청;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_조회_요청;
import static project.myblog.auth.dto.SocialType.NAVER;

class PostDocumentation extends Documentation {
    @Test
    void 포스트_작성() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        // when
        포스트_작성_요청(
                givenRestDocsFieldDescriptorRequestFields("post-create", getFieldDescriptorsRequest())
                , sessionId, "포스트1제목", "포스트1내용"
        );
    }

    @Test
    void 포스트_조회() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_요청(given(), sessionId, "포스트1제목", "포스트1내용");

        // when
        포스트_조회_요청(
                givenRestDocsFieldDescriptorRelaxedResponseFieldsAndPathParam("post-find",
                        getFieldDescriptors(),
                        getPathParameters()
                )
        );
    }

    @Test
    void 포스트_수정() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_요청(given(), sessionId, "포스트1제목", "포스트1내용");

        // when
        포스트_수정_요청(
                givenRestDocsFieldDescriptorRequestFieldsAndPathParam("post-update",
                        getFieldDescriptorsRequest(),
                        getPathParameters()
                )
                , sessionId, "포스트1제목 변경", "포스트1내용 변경"
        );
    }

    @Test
    void 포스트_삭제() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_요청(given(), sessionId, "포스트1제목", "포스트1내용");

        // when
        포스트_삭제_요청(givenRestDocsPathParam("post-delete", getPathParameters()), sessionId);
    }

    private FieldDescriptor[] getFieldDescriptorsRequest() {
        return new FieldDescriptor[] {
                fieldWithPath("title").description("포스트 제목"),
                fieldWithPath("contents").description("포스트 내용")
        };
    }

    private FieldDescriptor[] getFieldDescriptors() {
        return new FieldDescriptor[] {
                fieldWithPath("data.id").description("포스트 id"),
                fieldWithPath("data.title").description("포스트 제목"),
                fieldWithPath("data.contents").description("포스트 내용"),
                fieldWithPath("data.author").description(NAVER_EMAIL)
        };
    }

    private ParameterDescriptor[] getPathParameters() {
        return new ParameterDescriptor[] {
                parameterWithName("id").description("포스트 ID")
        };
    }
}
