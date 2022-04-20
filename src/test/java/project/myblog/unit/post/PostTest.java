package project.myblog.unit.post;

import org.junit.jupiter.api.Test;
import project.myblog.domain.Member;
import project.myblog.domain.Post;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

class PostTest {
    @Test
    void 포스트_수정() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        post.update("포스트1제목 변경", "포스트1내용 변경");

        // then
        Post expectedPost = new Post(1L, "포스트1제목 변경", "포스트1내용 변경", member);
        assertThat(post).isEqualTo(expectedPost);
    }

    @Test
    void 내_포스트_권한_검증_실패() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        boolean isAuthorization =  post.isAuthorization(new Member("member2@gmail.com"));

        assertThat(isAuthorization).isFalse();
    }

    @Test
    void 내_포스트_권한_검증_검증() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        boolean isAuthorization =  post.isAuthorization(member);

        assertThat(isAuthorization).isTrue();
    }
}
