package project.myblog.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import project.myblog.domain.redis.RedisHits;

@Component
public class HitsRedisRepository implements HitsRepository {
    private final RedisTemplate<String, Integer> redisTemplate;

    public HitsRedisRepository(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void increaseHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        RedisHits hits = getHits(postId);
        hashOperations.put(key, hashKey, hits.increase());
    }

    @Override
    public RedisHits getHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        return new RedisHits(postId, hashOperations.get(key, hashKey));
    }

    @Override
    public void deleteHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        hashOperations.delete(key, hashKey);
    }

    @Override
    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}
