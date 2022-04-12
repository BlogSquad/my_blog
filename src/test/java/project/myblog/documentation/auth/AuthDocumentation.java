package project.myblog.documentation.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.request.ParameterDescriptor;
import project.myblog.documentation.Documentation;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;
import static project.myblog.config.WebConfig.SESSION_LOGIN_URI;

class AuthDocumentation extends Documentation {
    @Test
    void 세션_로그인_네이버() {
        로그인_요청("auth-naver", AUTHORIZATION_CODE, NAVER.getServiceName());
    }

    @Test
    void 세션_로그인_깃허브() {
        로그인_요청("auth-github", AUTHORIZATION_CODE, GITHUB.getServiceName());
    }

    private ExtractableResponse<Response> 로그인_요청(String identifier, String authorizationCode, String serviceName) {
        return givenRestDocs(identifier, getParameterDescriptors())
                .queryParam("code", authorizationCode)
                .when().get(SESSION_LOGIN_URI + "/" + serviceName)
                .then().log().all().extract();
    }

    private ParameterDescriptor[] getParameterDescriptors() {
        return new ParameterDescriptor[] {
                parameterWithName("code").description("소셜 로그인 승인 코드(Authorization Code)")
        };
    }
}
