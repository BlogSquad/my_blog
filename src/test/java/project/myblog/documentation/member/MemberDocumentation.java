package project.myblog.documentation.member;

import org.junit.jupiter.api.Test;
import project.myblog.documentation.Documentation;

import java.util.Map;

import static project.myblog.acceptance.member.MemberSteps.회원_생성_요청;
import static project.myblog.acceptance.member.MemberSteps.회원_생성_정보;

class MemberDocumentation extends Documentation {
    @Test
    void 회원_생성() {
        // given
        Map<String, String> params = 회원_생성_정보();

        // when
        회원_생성_요청(givenRestDocs("member"), params);
    }
}
