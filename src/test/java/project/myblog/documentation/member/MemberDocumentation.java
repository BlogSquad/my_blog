package project.myblog.documentation.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import project.myblog.documentation.Documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static project.myblog.acceptance.auth.AuthSteps.로그인_됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;

class MemberDocumentation extends Documentation {
    @Test
    void 내_회원_정보_조회() {
        ExtractableResponse<Response> loginResponse = 로그인_요청(AUTHORIZATION_CODE, NAVER.getServiceName());
        String sessionId = loginResponse.sessionId();
        로그인_됨(loginResponse);

        내_회원_정보_조회_요청("member", sessionId);
    }

    private ExtractableResponse<Response> 내_회원_정보_조회_요청(String identifier, String sessionId) {
        return givenRestDocs(identifier, getFieldDescriptors())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .when().get("members/me")
                .then().log().all().extract();
    }

    private FieldDescriptor[] getFieldDescriptors() {
        return new FieldDescriptor[] {
                fieldWithPath("email").description("이메일"),
                fieldWithPath("introduction").description("한줄 소개"),
                fieldWithPath("subject").description("monkeyDugi(이메일 아이디)")
        };
    }
}
