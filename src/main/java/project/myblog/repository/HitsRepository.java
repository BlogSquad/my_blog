package project.myblog.repository;

public interface HitsRepository {
    void increaseHits(Long postId);
    Integer getHits(Long postId);
    void deleteHits(Long postId);
    void updateRDB();
    void flushAll();
}
