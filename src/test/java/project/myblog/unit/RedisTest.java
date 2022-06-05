package project.myblog.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.HitsRedisRepository;
import project.myblog.repository.MemberRepository;
import project.myblog.repository.PostRepository;
import project.myblog.UnitTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;


public class RedisTest extends UnitTest {
    @Autowired
    private HitsRedisRepository hitsRedisRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 조회수_증가() {
        // given
        Long postId = 1L;

        // when
        hitsRedisRepository.incrementHits(postId);
        hitsRedisRepository.incrementHits(postId);

        // then
        assertThat(hitsRedisRepository.getHits(postId)).isEqualTo(2);
    }

    @Test
    void 조회수_증가_동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            executorService.execute(() -> {
                hitsRedisRepository.incrementHits(1L);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        Integer hits = hitsRedisRepository.getHits(1L);
        assertThat(hits).isEqualTo(10);
    }

    @EnabledIfEnvironmentVariable(
            named="SPRING_PROFILES_ACTIVE",
            matches="real")
    @DisplayName("RDB 반영 중 조회수 증가할 경우 동시성 테스트")
    @Transactional
    @Test
    void updateRDB_동시성_테스트() throws InterruptedException {
        // given
        Member member = new Member(NAVER_EMAIL);
        memberRepository.save(member);

        Post post = new Post("포스트1제목", "포스트1내용", member);
        Long postId = postRepository.save(post).getId();

        int hitsCount = 5000;
        CountDownLatch countDownLatch = new CountDownLatch(hitsCount);

        // when
        scheduleUpdateRDB();
        incrementHitsConcurrently(hitsCount, countDownLatch, postId);

        hitsRedisRepository.updateRDB();

        // then
        Integer hits = postRepository.findById(postId).get().getHits();

        assertThat(hitsRedisRepository.getHits(postId)).isNull();
        assertThat(hits).isEqualTo(hitsCount);
    }

    @EnabledIfEnvironmentVariable(
            named="SPRING_PROFILES_ACTIVE",
            matches="real")
    @Transactional
    @Test
    void 조회수_RDB_반영() {
        // given
        Member member = new Member(NAVER_EMAIL);
        memberRepository.save(member);

        Post post = new Post("포스트1제목", "포스트1내용", member);
        Long postId = postRepository.save(post).getId();

        hitsRedisRepository.incrementHits(postId);

        // when
        hitsRedisRepository.updateRDB();

        // then
        assertThat(hitsRedisRepository.getHits(postId)).isNull();
        assertThat(postRepository.findById(postId).get().getHits()).isEqualTo(1);
    }

    private void scheduleUpdateRDB() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hitsRedisRepository.updateRDB();
            }
        });
        thread.start();
    }

    private void incrementHitsConcurrently(int hitsCount, CountDownLatch countDownLatch, Long postId) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= hitsCount; i++) {
            executorService.execute(() -> {
                hitsRedisRepository.incrementHits(postId);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
    }
}
