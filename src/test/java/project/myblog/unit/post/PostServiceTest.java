package project.myblog.unit.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.member.Member;
import project.myblog.domain.post.Post;
import project.myblog.repository.member.MemberRepository;
import project.myblog.repository.post.PostRepository;
import project.myblog.service.post.PostService;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.response.PostResponse;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
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
        Member member = memberRepository.save(createMember());

        String title = "포스트1제목";
        String contents = "포스트1내용";
        PostRequest postRequest = new PostRequest(title, contents);

        // when
        Long postId = postService.createPost(EMAIL, postRequest);

        // then
        Post post = postRepository.findById(postId).get();
        Post expectedPost = new Post(postId, title, contents, member);

        assertThat(post).isEqualTo(expectedPost);
    }

    @Test
    void 포스트_조회() {
        // given
        memberRepository.save(createMember());

        String title = "포스트1제목".toUpperCase(Locale.ROOT);
        String contents = "포스트1내용";
        PostRequest postRequest = new PostRequest(title, contents);

        Long postId = postService.createPost(EMAIL, postRequest);

        // when
        PostResponse findPost = postService.findPost(postId);

        // then
        PostResponse postResponse = new PostResponse(postId, title, contents, EMAIL);
        assertThat(findPost).isEqualTo(postResponse);
    }

    private Member createMember() {
        return new Member(EMAIL);
    }
}
