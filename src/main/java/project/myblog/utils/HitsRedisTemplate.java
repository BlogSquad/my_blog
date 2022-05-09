package project.myblog.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class HitsRedisTemplate {
    private final RedisTemplate<String, Integer> redisTemplate;

    public HitsRedisTemplate(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void putHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        Integer hits = getHits(postId);
        if (hits != null) {
            hashOperations.put(key, hashKey, ++hits);
        } else {
            hashOperations.put(key, hashKey, 1);
        }
    }

    public Integer getHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        return hashOperations.get(key, hashKey);
    }

    public void deleteHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        hashOperations.delete(key, hashKey);
    }

    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}
