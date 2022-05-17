package project.myblog.repository;

public interface HitsRepository {
    void incrementHits(Long postId);
    Integer getHits(Long postId);
    Integer getAndDel(Long postId);
    void updateRDB();
    void flushAll();
}
