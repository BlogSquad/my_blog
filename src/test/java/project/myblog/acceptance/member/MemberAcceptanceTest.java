package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.myblog.AcceptanceTest;

import java.util.Map;

import static project.myblog.acceptance.member.MemberSteps.회원_생성_요청;
import static project.myblog.acceptance.member.MemberSteps.회원_생성_정보;
import static project.myblog.acceptance.member.MemberSteps.회원_생성됨;

@DisplayName("회원 정보 관리")
class MemberAcceptanceTest extends AcceptanceTest {
    /**
     * Scenario 회원가입
     * When 회원 생성을 요청한다
     * Then 회원 생성된다
     */
    @Test
    void 회원_생성() {
        // given
        Map<String, String> params = 회원_생성_정보();

        // when
        ExtractableResponse<Response> response = 회원_생성_요청(params);

        // then
        회원_생성됨(response);
    }
}
