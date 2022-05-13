package project.myblog.unit.post;

import org.junit.jupiter.api.Test;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

class PostTest {
    @Test
    void 포스트_수정() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        post.update("포스트1제목 변경", "포스트1내용 변경", member);

        // then
        Post expectedPost = new Post(1L, "포스트1제목 변경", "포스트1내용 변경", member);
        assertThat(post).isEqualTo(expectedPost);
    }

    @Test
    void 포스트_삭제() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        post.delete(member);

        // then
        assertThat(post.isDeleted()).isTrue();
    }

    @Test
    void 조회수_증가() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when
        post.increaseHits(10);

        // then
        assertThat(post.getHits()).isEqualTo(10);
    }

    @Test
    void 예외_타인_포스트_수정_실패() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when, then
        assertThatThrownBy(() ->
                post.update("포스트1제목 변경", "포스트1내용 변경", new Member("member2@gmail.com")))
                .isInstanceOf(BusinessException.class);

    }

    @Test
    void 예외_타인_포스트_삭제_실패() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post(1L, "포스트1제목", "포스트1내용", member);

        // when, then
        assertThatThrownBy(() ->
                post.delete(new Member("member2@gmail.com")))
                .isInstanceOf(BusinessException.class);
    }
}
