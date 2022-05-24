package project.myblog.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.post.PostStepsRequest.포스트_목록_조회_요청;
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
    void 포스트_목록_조회() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        포스트_작성_요청(given(), sessionId, "포스트1제목", "포스트1내용");
        포스트_작성_요청(given(), sessionId, "포스트2제목", "포스트2내용");

        FieldDescriptor[] fieldDescriptors = {
                fieldWithPath("data.posts[].id").description("포스트 id"),
                fieldWithPath("data.posts[].title").description("포스트 제목"),
                fieldWithPath("data.posts[].contents").description("포스트 내용"),
                fieldWithPath("data.posts[].author").description("작성자 이메일"),
                fieldWithPath("data.posts[].hits").description("조회수"),
                fieldWithPath("data.posts[].createDate").description("생성일시"),
                fieldWithPath("data.posts[].modifiedDate").description("수정일시"),
                fieldWithPath("data.totalCount").description("총 포스트 개수"),
                fieldWithPath("data.pageSize").description("페이지 사이즈"),
                fieldWithPath("data.currentPage").description("현재 페이지"),
                fieldWithPath("data.totalPage").description("총 페이지"),
        };

        // when
        int page = 0;
        포스트_목록_조회_요청(
                givenRestDocsRequestParametersRelaxedResponseFields("post-findAllPaging",
                        getParameterDescriptors(),
                        fieldDescriptors
                ), page
        );
    }
    private ParameterDescriptor[] getParameterDescriptors() {
        return new ParameterDescriptor[] {
                parameterWithName("page").description("요청 페이지")
        };
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
                fieldWithPath("data.author").description("작성자 이메일"),
                fieldWithPath("data.hits").description("조회수"),
                fieldWithPath("data.createDate").description("생성일시"),
                fieldWithPath("data.modifiedDate").description("수정일시")
        };
    }

    private ParameterDescriptor[] getPathParameters() {
        return new ParameterDescriptor[] {
                parameterWithName("id").description("포스트 ID")
        };
    }
}
