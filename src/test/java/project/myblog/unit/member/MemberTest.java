package project.myblog.unit.member;

import org.junit.jupiter.api.Test;
import project.myblog.domain.Member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

class MemberTest {
    @Test
    void 회원_탈퇴() {
        // given
        Member member = new Member(NAVER_EMAIL);

        // when
        member.delete();

        // then
        assertThat(member.isDeleted()).isTrue();
    }
}
