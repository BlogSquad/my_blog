package project.myblog.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTest {
    @Autowired
    private HitsRedisTemplate hitsRedisTemplate;

    @BeforeEach
    void setUp() {
        hitsRedisTemplate.flushAll();
    }

    @Test
    void 조회수_증가() {
        // given
        Long postId = 1L;

        // when
        hitsRedisTemplate.putHits(postId);
        hitsRedisTemplate.putHits(postId);

        // then
        assertThat(hitsRedisTemplate.getHits(postId)).isEqualTo(2);
    }
}
