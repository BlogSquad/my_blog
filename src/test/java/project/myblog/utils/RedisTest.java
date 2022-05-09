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
        hitsRedisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Test
    void 조회수_저장() {
        // given
        Long postId1 = 1L;
        Long postId2 = 2L;
        String hashKey = "hits";

        // when
        hitsRedisTemplate.putHits(postId1);
        hitsRedisTemplate.putHits(postId2);

        // then
        assertThat(hitsRedisTemplate.getHits(postId1)).isEqualTo(1);
        assertThat(hitsRedisTemplate.getHits(postId2)).isEqualTo(1);
    }

    @Test
    void 존재하는_포스트_조회수_증가() {
        // given
        Long postId = 1L;
        String hashKey = "hits";

        hitsRedisTemplate.putHits(postId);

        // when
        hitsRedisTemplate.putHits(postId);

        // then
        assertThat(hitsRedisTemplate.getHits(postId)).isEqualTo(2);
    }
}
