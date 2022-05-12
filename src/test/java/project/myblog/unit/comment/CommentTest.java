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
    void 댓글과_대댓글_모두_삭제된_경우_체크() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        Comment childComment1 = new Comment("대댓글1", post, member);
        Comment childComment2 = new Comment("대댓글2", post, member);
        comment.createChildComment(childComment1);
        comment.createChildComment(childComment2);

        comment.delete(member);
        childComment1.delete(member);
        childComment2.delete(member);

        // when
        boolean isAllDeleted = comment.isAllDeleted();

        // then
        assertThat(isAllDeleted).isTrue();
    }

    @Test
    void 댓글은_삭제되고_대댓글은_남아있는_경우_체크() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        Comment childComment1 = new Comment("대댓글1", post, member);
        Comment childComment2 = new Comment("대댓글2", post, member);
        comment.createChildComment(childComment1);
        comment.createChildComment(childComment2);

        comment.delete(member);
        childComment1.delete(member);

        // when
        boolean isAllDeleted = comment.isAllDeleted();

        // then
        assertThat(isAllDeleted).isFalse();
    }

    @Test
    void 댓글은_있고_대댓글은_삭제된_경우_체크() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        Comment childComment = new Comment("대댓글1", post, member);
        comment.createChildComment(childComment);

        childComment.delete(member);

        // when
        boolean isAllDeleted = comment.isAllDeleted();

        // then
        assertThat(isAllDeleted).isFalse();
    }

    @Test
    void 댓글과_대댓글_모두_삭제되지_않은_경우_체크() {
        // given
        Member member = new Member(NAVER_EMAIL);
        Post post = new Post("포스트1제목", "포스트1내용", member);
        Comment comment = new Comment("댓글", post, member);

        Comment childComment = new Comment("대댓글1", post, member);
        comment.createChildComment(childComment);

        // when
        boolean isAllDeleted = comment.isAllDeleted();

        // then
        assertThat(isAllDeleted).isFalse();
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
