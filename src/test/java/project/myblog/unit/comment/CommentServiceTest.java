package project.myblog.unit.comment;

import org.junit.jupiter.api.DisplayName;
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

import java.util.List;

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

    @DisplayName("댓글과 대댓글 목록을 조회한다.")
    @Test
    void 댓글_목록_조회() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        Long parentCommentId1 = commentService.createComment(NAVER_EMAIL, postId, new CommentRequest("댓글1"));
        Long parentCommentId2 = commentService.createComment(NAVER_EMAIL, postId, new CommentRequest("댓글2"));

        // when
        commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId1, new CommentRequest("대댓글1"));
        commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId1, new CommentRequest("대댓글2"));

        commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId2, new CommentRequest("대댓글3"));
        commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId2, new CommentRequest("대댓글4"));

        // then
        List<CommentResponse> commentResponses = commentService.findComments(postId).getComments();

        assertThat(commentResponses.size()).isEqualTo(2);
        assertThatFirstComment(parentCommentId1, commentResponses);
        assertThatSecondComment(parentCommentId2, commentResponses);
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

    @Test
    void 대댓글_작성() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentRequest = new CommentRequest("댓글1");
        Long parentCommentId = commentService.createComment(NAVER_EMAIL, postId, commentRequest);

        // when
        CommentRequest childCommentRequest1 = new CommentRequest("대댓글1");
        commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId, childCommentRequest1);
        CommentRequest childCommentRequest2 = new CommentRequest("대댓글2");
        commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId, childCommentRequest2);

        // then
        Comment comment = commentRepository.findById(parentCommentId).get();
        List<Comment> children = comment.getChildren();

        assertThat(children.size()).isEqualTo(2);

        assertThat(children.get(0).getParent().getId()).isEqualTo(parentCommentId);
        assertThat(children.get(1).getParent().getId()).isEqualTo(parentCommentId);

        assertThat(children.get(0).getContents()).isEqualTo("대댓글1");
        assertThat(children.get(1).getContents()).isEqualTo("대댓글2");
    }

    @DisplayName("대대댓글 작성 시 댓글에 대댓글로 작성된다.")
    @Test
    void 대대댓글_작성() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        CommentRequest commentRequest = new CommentRequest("댓글1");
        Long parentCommentId = commentService.createComment(NAVER_EMAIL, postId, commentRequest);

        CommentRequest childCommentRequest1 = new CommentRequest("대댓글1");
        Long nestedCommentId = commentService.createNestedComment(NAVER_EMAIL, postId, parentCommentId, childCommentRequest1);

        // when
        CommentRequest childCommentRequest2 = new CommentRequest("대대댓글1");
        commentService.createNestedComment(NAVER_EMAIL, postId, nestedCommentId, childCommentRequest2);

        // then
        Comment comment = commentRepository.findById(parentCommentId).get();
        List<Comment> children = comment.getChildren();

        assertThat(children.size()).isEqualTo(2);

        assertThat(children.get(0).getParent().getId()).isEqualTo(parentCommentId);
        assertThat(children.get(1).getParent().getId()).isEqualTo(parentCommentId);

        assertThat(children.get(0).getContents()).isEqualTo("대댓글1");
        assertThat(children.get(1).getContents()).isEqualTo("대대댓글1");
    }

    private void assertThatFirstComment(Long parentCommentId, List<CommentResponse> commentResponses) {
        assertThat(commentResponses.get(0).getParentId()).isNull();
        assertThat(commentResponses.get(0).getContents()).isEqualTo("댓글1");
        assertThat(commentResponses.get(0).getAuthor()).isEqualTo(NAVER_EMAIL);

        assertThat(commentResponses.get(0).getChildren().get(0).getParentId()).isEqualTo(parentCommentId);
        assertThat(commentResponses.get(0).getChildren().get(0).getContents()).isEqualTo("대댓글1");
        assertThat(commentResponses.get(0).getChildren().get(0).getAuthor()).isEqualTo(NAVER_EMAIL);

        assertThat(commentResponses.get(0).getChildren().get(1).getParentId()).isEqualTo(parentCommentId);
        assertThat(commentResponses.get(0).getChildren().get(1).getContents()).isEqualTo("대댓글2");
        assertThat(commentResponses.get(0).getChildren().get(1).getAuthor()).isEqualTo(NAVER_EMAIL);
    }

    private void assertThatSecondComment(Long parentCommentId, List<CommentResponse> commentResponses) {
        assertThat(commentResponses.get(1).getParentId()).isNull();
        assertThat(commentResponses.get(1).getContents()).isEqualTo("댓글2");
        assertThat(commentResponses.get(1).getAuthor()).isEqualTo(NAVER_EMAIL);

        assertThat(commentResponses.get(1).getChildren().get(0).getParentId()).isEqualTo(parentCommentId);
        assertThat(commentResponses.get(1).getChildren().get(0).getContents()).isEqualTo("대댓글3");
        assertThat(commentResponses.get(1).getChildren().get(0).getAuthor()).isEqualTo(NAVER_EMAIL);

        assertThat(commentResponses.get(1).getChildren().get(1).getParentId()).isEqualTo(parentCommentId);
        assertThat(commentResponses.get(1).getChildren().get(1).getContents()).isEqualTo("대댓글4");
        assertThat(commentResponses.get(1).getChildren().get(1).getAuthor()).isEqualTo(NAVER_EMAIL);
    }

    private Member createMember(String email) {
        return new Member(email);
    }
}
