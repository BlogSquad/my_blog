package project.myblog.unit.member;

import org.junit.jupiter.api.Test;
import project.myblog.domain.Member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.EMAIL;

class MemberTest {
    @Test
    void delete() {
        // given
        Member member = new Member(EMAIL);

        // when
        member.delete();

        // then
        assertThat(member.isDeleted()).isTrue();
    }
}
