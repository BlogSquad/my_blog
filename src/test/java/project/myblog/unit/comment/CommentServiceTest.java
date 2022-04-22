package project.myblog.unit.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Comment;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.CommentRepository;
import project.myblog.repository.MemberRepository;
import project.myblog.service.CommentService;
import project.myblog.service.PostService;
import project.myblog.web.dto.comment.CommentRequest;
import project.myblog.web.dto.post.PostRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

@Transactional
@SpringBootTest
class CommentServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 댓글_작성() {
        // given
        Member member = memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        // when
        CommentRequest commentRequest = new CommentRequest("댓글1");
        Long commentId = commentService.createComments(NAVER_EMAIL, postId, commentRequest);

        // then
        Comment comment = commentRepository.findById(commentId).get();

        Post expectedPost = new Post(postId, "포스트1제목", "포스트1내용", member);
        Comment expectedComments = new Comment(commentId, "댓글1", expectedPost, member);

        assertThat(comment).isEqualTo(expectedComments);
    }

    private Member createMember(String email) {
        return new Member(email);
    }
}
