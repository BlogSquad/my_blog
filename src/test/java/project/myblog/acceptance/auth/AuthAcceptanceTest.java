package project.myblog.acceptance.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.acceptance.AcceptanceTest;

import static project.myblog.acceptance.auth.AuthSteps.로그인_안됨;
import static project.myblog.acceptance.auth.AuthSteps.로그인_요청;
import static project.myblog.auth.dto.SocialType.GITHUB;
import static project.myblog.auth.dto.SocialType.NAVER;

@DisplayName("로그인 인증, 인가 관리")
class AuthAcceptanceTest extends AcceptanceTest {
    /**
     * When 네이버 로그인 요청
     * Then 로그인 안됨
     */
    @Test
    void 네이버_세션_로그인_존재하지_않는_인증_코드() {
        ExtractableResponse<Response> response = 로그인_요청("notExistsAuthorizationCode", NAVER.getServiceName());

        로그인_안됨(response);
    }

    /**
     * When 깃허브 로그인 요청
     * Then 로그인 안됨
     */
    @Test
    void 깃허브_세션_로그인_존재하지_않는_인증_코드() {
        ExtractableResponse<Response> response = 로그인_요청("notExistsAuthorizationCode", GITHUB.getServiceName());

        로그인_안됨(response);
    }
}
