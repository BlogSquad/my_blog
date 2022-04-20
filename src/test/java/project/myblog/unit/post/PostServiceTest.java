package project.myblog.unit.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;
import project.myblog.repository.MemberRepository;
import project.myblog.repository.PostRepository;
import project.myblog.service.PostService;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.PostResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.myblog.acceptance.member.MemberStepsRequest.EMAIL;

@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 포스트_작성() {
        // given
        Member member = memberRepository.save(createMember(EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");

        // when
        Long postId = postService.createPost(EMAIL, postRequest);

        // then
        Post post = postRepository.findById(postId).get();
        Post expectedPost = new Post(postId, "포스트1제목", "포스트1내용", member);

        assertThat(post).isEqualTo(expectedPost);
    }

    @Test
    void 포스트_조회() {
        // given
        memberRepository.save(createMember(EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(EMAIL, postRequest);

        // when
        PostResponse findPost = postService.findPost(postId);

        // then
        PostResponse postResponse = new PostResponse(postId, "포스트1제목", "포스트1내용", EMAIL);
        assertThat(findPost).isEqualTo(postResponse);
    }

    @Test
    void 포스트_수정() {
        // given
        memberRepository.save(createMember(EMAIL));
        PostRequest postSaveRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(EMAIL, postSaveRequest);

        // when
        PostRequest postUpdateRequest = new PostRequest("포스트1제목 변경", "포스트1내용 변경");
        postService.updatePost(EMAIL, postId, postUpdateRequest);

        // then
        PostResponse findPost = postService.findPost(postId);
        PostResponse postResponse = new PostResponse(postId, "포스트1제목 변경", "포스트1내용 변경", EMAIL);
        assertThat(findPost).isEqualTo(postResponse);
    }

    @Test
    void 포스트_삭제() {
        // given
        memberRepository.save(createMember(EMAIL));
        PostRequest postSaveRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(EMAIL, postSaveRequest);

        // when
        postService.deletePost(EMAIL, postId);

        // then
        assertThatThrownBy(() -> postService.findPost(postId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 예외_존재하지_않는_포스트_조회_실패() {
        // when, then
        assertThatThrownBy(() -> postService.findPost(1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 예외_타인의_포스트_삭제_실패() {
        // given
        memberRepository.save(createMember(EMAIL));
        memberRepository.save(createMember("member2@gmail.com"));

        PostRequest postSaveRequest = new PostRequest("member2의 포스트제목", "member2의 포스트1내용");
        Long postId = postService.createPost("member2@gmail.com", postSaveRequest);

        // when, then
        assertThatThrownBy(() -> postService.deletePost(EMAIL, postId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 존재하지_않는_포스트_삭제_실패() {
        // given
        memberRepository.save(createMember(EMAIL));

        // when, then
        assertThatThrownBy(() -> postService.deletePost(EMAIL, 1L))
                .isInstanceOf(BusinessException.class);
    }

    private Member createMember(String email) {
        return new Member(email);
    }
}
