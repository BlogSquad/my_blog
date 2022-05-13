package project.myblog.repository;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public void incrementHits(Long postId) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        String key = "posts:" + postId + "hits";

        operations.increment(key);
    }

    @Override
    public Integer getHits(Long postId) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        String key = "posts:" + postId + "hits";

        return operations.get(key.toString());
    }

    @Override
    public Integer getAndDel(Long postId) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        String key = "posts:" + postId + "hits";

        return operations.getAndDelete(key.toString());
    }

    @Transactional
    @Override
    public void updateRDB() {
        ScanOptions scanOptions = ScanOptions.scanOptions().match(SCAN_MATCH_PATTERN).count(SCAN_MATCH_LIMIT_COUNT).build();
        Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        while (keys.hasNext()) {
            Long postId = extractPostId(keys);
            postRepository.findById(postId)
                    .ifPresent(post -> post.increaseHits(getAndDel(postId)));
        }
    }

    @Override
    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    private Long extractPostId(Cursor<byte[]> keys) {
        String key = new String(keys.next());
        int index = key.indexOf(":");
        int index1 = key.indexOf("hits");

        return Long.valueOf(key.substring(index + 1, index1));
    }
}
