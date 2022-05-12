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
        assertThat(hitsRedisRepository.getHits(postId)).isEqualTo(2);
    }

    @Transactional
    @Test
    void 조회수_RDB에_반영() {
        // given
        Member member = new Member(NAVER_EMAIL);
        memberRepository.save(member);

        Post post = new Post("포스트1제목", "포스트1내용", member);
        Long postId = postRepository.save(post).getId();

        hitsRedisRepository.increaseHits(postId);
        hitsRedisRepository.increaseHits(postId);

        // when
        hitsRedisRepository.updateRDB();

        // then
        assertThat(hitsRedisRepository.getHits(postId)).isNull();

        Post findPost = postRepository.findById(postId).get();
        assertThat(findPost.getHits()).isEqualTo(2);
    }

    @Test
    void 조회수_증가_동시성_테스트() {
        for (int i = 1; i <= 10_000; i++) {
            Runnable run = () -> hitsRedisRepository.increaseHits(1L);
            new Thread(run).start();
        }

        Integer hits = hitsRedisRepository.getHits(1L);
        assertThat(hits).isEqualTo(10_000);
    }

    @Test
    void 조회수_RDB_반영() {
        // given
        Member member = new Member(NAVER_EMAIL);
        memberRepository.save(member);

        Post post = new Post("포스트1제목", "포스트1내용", member);
        Long postId = postRepository.save(post).getId();

        hitsRedisRepository.increaseHits(postId);

        // when
        hitsRedisRepository.updateRDB();

        // then
        assertThat(hitsRedisRepository.getHits(postId)).isNull();
        assertThat(postRepository.findById(postId).get().getHits()).isEqualTo(1);
    }

    @Test
    void 조회수_RDB반영_동시성_테스트() {

    }
}
