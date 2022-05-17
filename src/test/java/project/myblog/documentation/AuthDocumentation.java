package project.myblog.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;
import static project.myblog.config.TestWebConfig.TestAbstractOAuthSessionAuthentication.AUTHORIZATION_CODE;

class AuthDocumentation extends Documentation {
    @Test
    void 세션_로그인_네이버() {
        로그인_요청(givenRestDocsRequestParameters("auth-naver", getParameterDescriptors()),
                                                AUTHORIZATION_CODE, NAVER.getServiceName());
    }

    @Test
    void 세션_로그인_깃허브() {
        로그인_요청(givenRestDocsRequestParameters("auth-github", getParameterDescriptors()),
                                                AUTHORIZATION_CODE, GITHUB.getServiceName());
    }

    private ParameterDescriptor[] getParameterDescriptors() {
        return new ParameterDescriptor[] {
                parameterWithName("code").description("소셜 로그인 승인 코드(Authentication Code)")
        };
    }
}
