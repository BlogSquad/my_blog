package project.myblog.unit.comment;

import org.junit.jupiter.api.Test;
import project.myblog.domain.Comment;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

class CommentTest {
    @Test
    void 댓글_수정() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        // when
        comment.update("댓글1 변경", member);

        // then
        Comment exceptedComment = new Comment("댓글1 변경", post, member);
        assertThat(comment).isEqualTo(exceptedComment);
    }

    @Test
    void 댓글_삭제() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        // when
        comment.delete(member);

        // then
        assertThat(comment.isDeleted()).isTrue();
    }

    @Test
    void 예외_타인_댓글_수정_실패() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        // when
        assertThatThrownBy(() ->
                comment.update("댓글1 변경", new Member("member2@gmail.com")))
                // then
                .isInstanceOf(BusinessException.class);
    }
}
