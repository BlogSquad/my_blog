package project.myblog.domain.redis;

public class RedisHits {
    private Long postId;
    private Integer count;

    public RedisHits(Long postId, Integer count) {
        this.postId = postId;
        this.count = count;
    }

    public Integer increase() {
        if (count != null) {
            return ++count;
        }
        return 1;
    }

    public Long getPostId() {
        return postId;
    }

    public Integer getCount() {
        return count;
    }
}
