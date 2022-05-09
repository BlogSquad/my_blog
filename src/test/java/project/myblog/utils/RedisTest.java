package project.myblog.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.myblog.repository.HitsRedisRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTest {
    @Autowired
    private HitsRedisRepository hitsRedisRepository;

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
}
