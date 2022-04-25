package project.myblog.unit.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Comment;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;
import project.myblog.repository.CommentRepository;
import project.myblog.repository.MemberRepository;
import project.myblog.service.CommentService;
import project.myblog.service.PostService;
import project.myblog.web.dto.comment.CommentRequest;
import project.myblog.web.dto.comment.CommentResponse;
import project.myblog.web.dto.post.PostRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        Long commentId = commentService.createComment(NAVER_EMAIL, postId, commentRequest);

        // then
        Comment comment = commentRepository.findById(commentId).get();

        Post expectedPost = new Post(postId, "포스트1제목", "포스트1내용", member);
        Comment expectedComments = new Comment(commentId, "댓글1", expectedPost, member);

        assertThat(comment).isEqualTo(expectedComments);
    }

    @Test
    void 댓글_조회() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        commentService.createComment(NAVER_EMAIL, postId, new CommentRequest("댓글1"));
        commentService.createComment(NAVER_EMAIL, postId, new CommentRequest("댓글2"));

        // when
        List<CommentResponse> commentResponses = commentService.findComments(postId);

        // then
        List<CommentResponse> expectedCommentResponses = new ArrayList<>();
        expectedCommentResponses.add(new CommentResponse("댓글1", NAVER_EMAIL));
        expectedCommentResponses.add(new CommentResponse("댓글2", NAVER_EMAIL));

        assertThat(commentResponses).isEqualTo(expectedCommentResponses);
    }

    @Test
    void 댓글_수정() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentCreateRequest = new CommentRequest("댓글1");
        Long commentId = commentService.createComment(NAVER_EMAIL, postId, commentCreateRequest);

        // when
        CommentRequest commentUpdateRequest = new CommentRequest("댓글1 수정");
        commentService.updateComment(NAVER_EMAIL, commentId, commentUpdateRequest);

        // then
        Comment comment = commentRepository.findById(commentId).get();
                assertThat(comment.getContents()).isEqualTo("댓글1 수정");
    }

    @Test
    void 댓글_삭제() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentCreateRequest = new CommentRequest("댓글1");
        Long commentId = commentService.createComment(NAVER_EMAIL, postId, commentCreateRequest);

        // when
        commentService.deleteComment(NAVER_EMAIL, commentId);

        // then
        assertThat(commentRepository.findById(commentId).get().isDeleted()).isTrue();
    }

    @Test
    void 예외_타인_댓글_수정_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        memberRepository.save(createMember("member2@gmail.com"));

        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentCreateRequest = new CommentRequest("댓글1");
        Long commentId = commentService.createComment("member2@gmail.com", postId, commentCreateRequest);

        CommentRequest commentUpdateRequest = new CommentRequest("댓글1 수정");

        // when, then
        assertThatThrownBy(() -> commentService.updateComment(NAVER_EMAIL, commentId, commentUpdateRequest))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 예외_존재하지_않는_댓글_수정_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));

        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentUpdateRequest = new CommentRequest("댓글1 수정");

        // when, then
        assertThatThrownBy(() -> commentService.updateComment(NAVER_EMAIL, 1L, commentUpdateRequest))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 예외_타인_댓글_삭제_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        memberRepository.save(createMember("member2@gmail.com"));

        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentCreateRequest = new CommentRequest("댓글1");
        Long commentId = commentService.createComment("member2@gmail.com", postId, commentCreateRequest);

        // when, then
        assertThatThrownBy(() -> commentService.deleteComment(NAVER_EMAIL, commentId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 예외_존재하지_않는_댓글_삭제_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));

        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        postService.createPost(NAVER_EMAIL, postRequest);

        // when, then
        assertThatThrownBy(() -> commentService.deleteComment(NAVER_EMAIL, 1L))
                .isInstanceOf(BusinessException.class);
    }

    private Member createMember(String email) {
        return new Member(email);
    }
}
