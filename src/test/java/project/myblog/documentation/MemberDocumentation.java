package project.myblog.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청_제목;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청_한줄_소개;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_조회_요청;
import static project.myblog.acceptance.member.MemberStepsRequest.회원_탈퇴_요청;
import static project.myblog.auth.dto.SocialType.NAVER;

class MemberDocumentation extends Documentation {
    @Test
    void 내_회원_정보_조회() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        // when
        내_회원_정보_조회_요청(
                givenRestDocsFieldDescriptorRelaxedResponseFields("member-findMemberOfMine", getFieldDescriptors())
                , sessionId);
    }

    @Test
    void 내_회원_정보_수정_한줄_소개() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        // when
        내_회원_정보_수정_요청_한줄_소개(
                givenRestDocsFieldDescriptorRequestFields("member-updateMemberOfMineIntroduction",
                        getFieldDescriptorsRequest("introduction", "한줄 소개"))
                , sessionId, "한줄 소개 변경"
        );
    }

    @Test
    void 내_회원_정보_수정_제목() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        // when
        내_회원_정보_수정_요청_제목(
                givenRestDocsFieldDescriptorRequestFields("member-updateMemberOfMineSubject",
                        getFieldDescriptorsRequest("subject", "제목"))
                , sessionId, "제목 변경"
        );
    }

    @Test
    void 회원_탈퇴() {
        // given
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());

        // when
        회원_탈퇴_요청(givenRestDocs("member-delete"), sessionId);
    }

    private FieldDescriptor[] getFieldDescriptors() {
        return new FieldDescriptor[] {
                fieldWithPath("data.email").description("이메일"),
                fieldWithPath("data.introduction").description("한줄 소개"),
                fieldWithPath("data.subject").description("블로그 제목")
        };
    }

    private FieldDescriptor[] getFieldDescriptorsRequest(String fieldWithPath, String description) {
        return new FieldDescriptor[] {
                fieldWithPath(fieldWithPath).description(description)
        };
    }
}
