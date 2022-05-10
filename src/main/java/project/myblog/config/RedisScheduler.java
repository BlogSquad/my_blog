package project.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import project.myblog.repository.HitsRepository;
import project.myblog.repository.PostRepository;

@Configuration
public class RedisScheduler {
    private final HitsRepository hitsRepository;
    private PostRepository postRepository;

    public RedisScheduler(HitsRepository hitsRepository, PostRepository postRepository) {
        this.hitsRepository = hitsRepository;
        this.postRepository = postRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void test() {
//        hitsRepository.트
    }
}
