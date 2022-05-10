package project.myblog.repository;

import project.myblog.domain.redis.RedisHits;

public interface HitsRepository {
    void increaseHits(Long postId);
    RedisHits getHits(Long postId);
    void deleteHits(Long postId);
    void updateRDB();
    void flushAll();
}
