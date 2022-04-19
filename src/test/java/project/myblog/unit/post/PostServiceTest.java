package project.myblog.unit.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.member.Member;
import project.myblog.domain.post.Post;
import project.myblog.repository.MemberRepository;
import project.myblog.repository.PostRepository;
import project.myblog.service.PostService;
import project.myblog.web.dto.post.PostRequest;

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

        // when
        String title = "첫 포스팅";
        String contents = "첫 포스팅을 작성했다.";
        PostRequest postRequest = new PostRequest(title, contents);

        postService.createPost(EMAIL, postRequest);

        // then
        Post post = postRepository.findById(1L).get();
        Post expectedPost = new Post(1L, title, contents, member);

        assertThat(post).isEqualTo(expectedPost);
    }

    private Member createMember() {
        return new Member(EMAIL);
    }
}
