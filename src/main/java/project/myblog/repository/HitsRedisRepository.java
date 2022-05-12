package project.myblog.repository;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Post;

@Component
public class HitsRedisRepository implements HitsRepository {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final PostRepository postRepository;

    private static final int SCAN_MATCH_LIMIT_COUNT = 10;
    private static final String SCAN_MATCH_PATTERN = "*";

    public HitsRedisRepository(RedisTemplate<String, Integer> redisTemplate, PostRepository postRepository) {
        this.redisTemplate = redisTemplate;
        this.postRepository = postRepository;
    }

    @Override
    public void increaseHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        hashOperations.increment(key, hashKey, 1);
    }

    @Override
    public Integer getHits(Long postId) {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        String key = "posts:" + postId;
        String hashKey = "hits";

        return hashOperations.get(key, hashKey);
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
        ScanOptions scanOptions = ScanOptions.scanOptions().match(SCAN_MATCH_PATTERN).count(SCAN_MATCH_LIMIT_COUNT).build();
        Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        while (keys.hasNext()) {
            Long postId = extractPostId(keys);
            Post post = postRepository.findById(postId).get();
            post.increaseHits(getHits(postId));
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
