package project.myblog.unit.post;

import org.junit.jupiter.api.Test;
import project.myblog.domain.member.Member;
import project.myblog.domain.post.Post;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.EMAIL;

class PostTest {
    @Test
    void 포스트_수정() {
        // given
        Member member = new Member(EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        post.update("포스트1제목 변경", "포스트1내용 변경");

        // then
        Post expectedPost = new Post(1L, "포스트1제목 변경", "포스트1내용 변경", member);
        assertThat(post).isEqualTo(expectedPost);
    }
}
