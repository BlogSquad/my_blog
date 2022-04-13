package project.myblog.documentation.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import project.myblog.documentation.Documentation;
import project.myblog.web.dto.MemberRequest;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.acceptance.member.MemberStepsAssert.로그인_요청_로그인_됨;
import static project.myblog.acceptance.member.MemberStepsRequest.내_회원_정보_수정_요청;
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
    void 내_회원_정보_수정() {
        String sessionId = 로그인_요청_로그인_됨(NAVER.getServiceName());
        MemberRequest memberRequest = new MemberRequest("한줄 소개 변경", "제목 변경");

        // when
        내_회원_정보_수정_요청(
                givenRestDocsFieldDescriptorRequestFields("member-updateMemberOfMine", getFieldDescriptorsRequest())
                , sessionId, memberRequest
        );
    }

    private FieldDescriptor[] getFieldDescriptors() {
        return new FieldDescriptor[] {
                fieldWithPath("email").description("이메일"),
                fieldWithPath("introduction").description("한줄 소개"),
                fieldWithPath("subject").description("블로그 제목")
        };
    }

    private FieldDescriptor[] getFieldDescriptorsRequest() {
        return new FieldDescriptor[] {
                fieldWithPath("introduction").description("소개"),
                fieldWithPath("subject").description("제목"),
        };
    }
}
