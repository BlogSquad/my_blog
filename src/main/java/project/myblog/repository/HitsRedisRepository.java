package project.myblog.repository;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Post;
import project.myblog.domain.redis.RedisHits;

@Component
public class HitsRedisRepository implements HitsRepository {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final PostRepository postRepository;

    public HitsRedisRepository(RedisTemplate<String, Integer> redisTemplate, PostRepository postRepository) {
        this.redisTemplate = redisTemplate;
        this.postRepository = postRepository;
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

    @Transactional
    @Override
    public void updateRDB() {
        ScanOptions scanOptions = ScanOptions.scanOptions().match("*").count(10).build();
        Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        while (keys.hasNext()) {
            Long postId = extractPostId(keys);
            Post post = postRepository.findById(postId).get();
            post.increaseHits(getHits(postId).getCount());
        }
        flushAll();
    }

    @Override
    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    private Long extractPostId(Cursor<byte[]> keys) {
        String key = new String(keys.next());
        int index = key.indexOf(":");

        return Long.valueOf(key.substring(index + 1));
    }
}
