package project.myblog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import project.myblog.repository.HitsRepository;

@Configuration
public class RedisScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HitsRepository hitsRepository;

    public RedisScheduler(HitsRepository hitsRepository) {
        this.hitsRepository = hitsRepository;
    }

    @Scheduled(fixedDelay = 3_600_000)
    private void updateRDB() {
        logger.info("redis 조회수 RDB update start");
        hitsRepository.updateRDB();
    }
}
