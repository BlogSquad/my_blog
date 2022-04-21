package project.myblog.unit.comments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Comments;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.CommentsRepository;
import project.myblog.repository.MemberRepository;
import project.myblog.service.CommentsService;
import project.myblog.service.PostService;
import project.myblog.web.dto.comments.CommentsRequest;
import project.myblog.web.dto.post.PostRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

@Transactional
@SpringBootTest
class CommentsServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Test
    void 댓글_작성() {
        // given
        Member member = memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        // when
        CommentsRequest commentsRequest = new CommentsRequest("댓글1");
        Long commentsId = commentsService.createComments(NAVER_EMAIL, postId, commentsRequest);

        // then
        Comments comments = commentsRepository.findById(commentsId).get();

        Post expectedPost = new Post(postId, "포스트1제목", "포스트1내용", member);
        Comments expectedComments = new Comments(commentsId, "댓글1", expectedPost, member);

        assertThat(comments).isEqualTo(expectedComments);
    }

    private Member createMember(String email) {
        return new Member(email);
    }
}
