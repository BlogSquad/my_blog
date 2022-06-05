package project.myblog.unit.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;
import project.myblog.repository.HitsRepository;
import project.myblog.repository.MemberRepository;
import project.myblog.repository.PostRepository;
import project.myblog.service.PostService;
import project.myblog.UnitTest;
import project.myblog.web.dto.post.PostPagingResponses;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.PostResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

class PostServiceTest extends UnitTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HitsRepository hitsRepository;

    @Test
    void 포스트_작성() {
        // given
        Member member = memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");

        // when
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        // then
        Post post = postRepository.findById(postId).get();
        Post expectedPost = new Post(postId, "포스트1제목", "포스트1내용", member);

        assertThat(post).isEqualTo(expectedPost);
    }

    @Test
    void 포스트_조회() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postRequest);

        // when
        PostResponse findPost = postService.findPost(postId);

        // then
        PostResponse postResponse = new PostResponse(postId, "포스트1제목", "포스트1내용", NAVER_EMAIL, 0);
        assertThat(findPost).isEqualTo(postResponse);
    }

    @Test
    void 포스트_수정() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postSaveRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postSaveRequest);

        // when
        PostRequest postUpdateRequest = new PostRequest("포스트1제목 변경", "포스트1내용 변경");
        postService.updatePost(NAVER_EMAIL, postId, postUpdateRequest);

        // then
        PostResponse findPost = postService.findPost(postId);
        PostResponse postResponse = new PostResponse(postId, "포스트1제목 변경", "포스트1내용 변경", NAVER_EMAIL, 0);
        assertThat(findPost).isEqualTo(postResponse);
    }

    @Test
    void 포스트_삭제() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postSaveRequest = new PostRequest("포스트1제목", "포스트1내용");
        Long postId = postService.createPost(NAVER_EMAIL, postSaveRequest);

        // when
        postService.deletePost(NAVER_EMAIL, postId);

        // then
        assertThat(postRepository.findById(postId).get().isDeleted()).isTrue();
    }

    @EnabledIfEnvironmentVariable(
            named="SPRING_PROFILES_ACTIVE",
            matches="real")
    @DisplayName("페이징 기반으로 조회힌다.")
    @Test
    void 포스트_목록_조회() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        // 포스트 11개 생성
        for (int i = 1; i <= 11; i++) {
            PostRequest postRequest = new PostRequest("포스트제목" + i, "포스트1내용" + i);
            postService.createPost(NAVER_EMAIL, postRequest);
        }

        incrementHits();

        // when
        String[] sort = {"hits", "createDate"};
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, sort);
        PostPagingResponses findPosts = postService.findAllPostPaging(pageRequest);

        // then
        assertThat(findPosts.getPosts().get(0).getId()).isEqualTo(5L);
        assertThat(findPosts.getPosts().get(1).getId()).isEqualTo(1L);
        assertThat(findPosts.getPosts().get(2).getId()).isEqualTo(3L);
        assertThat(findPosts.getTotalCount()).isEqualTo(11);
        assertThat(findPosts.getPageSize()).isEqualTo(10);
        assertThat(findPosts.getCurrentPage()).isEqualTo(0);
        assertThat(findPosts.getTotalPage()).isEqualTo(2);
    }

    @Test
    void 예외_타인_포스트_수정_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        memberRepository.save(createMember("member2@gmail.com"));

        PostRequest postSaveRequest = new PostRequest("member2의 포스트1제목", "member2의 포스트1내용");
        Long postId = postService.createPost("member2@gmail.com", postSaveRequest);

        PostRequest postUpdateRequest = new PostRequest("member2의 포스트1제목 변경", "member2의 포스트1내용 변경");

        // when, then
        assertThatThrownBy(() -> postService.updatePost(NAVER_EMAIL, postId, postUpdateRequest))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 존재하지_않는_포스트_수정_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));
        PostRequest postUpdateRequest = new PostRequest("member2의 포스트1제목 변경", "member2의 포스트1내용 변경");

        // when, then
        assertThatThrownBy(() -> postService.updatePost(NAVER_EMAIL, 1L, postUpdateRequest))
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
        memberRepository.save(createMember(NAVER_EMAIL));
        memberRepository.save(createMember("member2@gmail.com"));

        PostRequest postSaveRequest = new PostRequest("member2의 포스트1제목", "member2의 포스트1내용");
        Long postId = postService.createPost("member2@gmail.com", postSaveRequest);

        // when, then
        assertThatThrownBy(() -> postService.deletePost(NAVER_EMAIL, postId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 존재하지_않는_포스트_삭제_실패() {
        // given
        memberRepository.save(createMember(NAVER_EMAIL));

        // when, then
        assertThatThrownBy(() -> postService.deletePost(NAVER_EMAIL, 1L))
                .isInstanceOf(BusinessException.class);
    }

    private Member createMember(String email) {
        return new Member(email);
    }

    private void incrementHits() {
        hitsRepository.incrementHits(1L);
        hitsRepository.incrementHits(1L);
        hitsRepository.incrementHits(3L);
        hitsRepository.incrementHits(5L);
        hitsRepository.incrementHits(5L);
        hitsRepository.incrementHits(5L);
        hitsRepository.updateRDB();
    }
}
