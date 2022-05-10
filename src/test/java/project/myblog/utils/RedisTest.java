package project.myblog.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.HitsRedisRepository;
import project.myblog.repository.MemberRepository;
import project.myblog.repository.PostRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;

@SpringBootTest
public class RedisTest {
    @Autowired
    private HitsRedisRepository hitsRedisRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        hitsRedisRepository.flushAll();
    }

    @Test
    void 조회수_증가() {
        // given
        Long postId = 1L;

        // when
        hitsRedisRepository.increaseHits(postId);
        hitsRedisRepository.increaseHits(postId);

        // then
        assertThat(hitsRedisRepository.getHits(postId).getCount()).isEqualTo(2);
    }

    @Transactional
    @Test
    void 조회수_RDB에_반영() {
        // given
        Member member = new Member(NAVER_EMAIL);
        memberRepository.save(member);

        Long postId = 1L;
        Post post = new Post("포스트1제목", "포스트1내용", member);
        postRepository.save(post);

        hitsRedisRepository.increaseHits(postId);
        hitsRedisRepository.increaseHits(postId);

        // when
        hitsRedisRepository.updateRDB();

        // then
        assertThat(hitsRedisRepository.getHits(postId).getCount()).isNull();

        Post findPost = postRepository.findById(1L).get();
        assertThat(findPost.getHits()).isEqualTo(2);
    }
}
