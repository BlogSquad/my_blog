package project.myblog.documentation.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import project.myblog.documentation.Documentation;
import project.myblog.web.dto.member.request.MemberIntroductionRequest;
import project.myblog.web.dto.member.request.MemberSubjectRequest;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청_제목;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청_한줄_소개;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_조회_요청;
import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;

class MemberDocumentation extends Documentation {
    @Test
    void 내_회원_정보_조회() {
        // given
        ExtractableResponse<Response> loginResponse = 로그인_요청(given(), AUTHORIZATION_CODE, NAVER.getServiceName());
        String sessionId = loginResponse.sessionId();
        로그인_됨(loginResponse);

        // when
        내_회원_정보_조회_요청(
                givenRestDocsFieldDescriptorRelaxedResponseFields("member-findMemberOfMine", getFieldDescriptors()),
                sessionId);
    }

    @Test
    void 내_회원_정보_수정_한줄_소개() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        MemberIntroductionRequest memberIntroductionRequest = new MemberIntroductionRequest("한줄 소개 변경");

        // when
        내_회원_정보_수정_요청_한줄_소개(
                givenRestDocsFieldDescriptorRequestFields("member-updateMemberOfMineIntroduction",
                        getFieldDescriptorsRequest("introduction", "한줄 소개"))
                , sessionId, memberIntroductionRequest
        );
    }

    @Test
    void 내_회원_정보_수정_제목() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        MemberSubjectRequest memberSubjectRequest = new MemberSubjectRequest("제목 변경");

        // when
        내_회원_정보_수정_요청_제목(
                givenRestDocsFieldDescriptorRequestFields("member-updateMemberOfMineSubject",
                        getFieldDescriptorsRequest("subject", "제목"))
                , sessionId, memberSubjectRequest
        );
    }

    private FieldDescriptor[] getFieldDescriptors() {
        return new FieldDescriptor[] {
                fieldWithPath("email").description("이메일"),
                fieldWithPath("introduction").description("한줄 소개"),
                fieldWithPath("subject").description("블로그 제목")
        };
    }

    private FieldDescriptor[] getFieldDescriptorsRequest(String fieldWithPath, String description) {
        return new FieldDescriptor[] {
                fieldWithPath(fieldWithPath).description(description)
        };
    }
}
