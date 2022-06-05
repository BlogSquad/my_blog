package project.myblog;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.config.TestWebConfig;
import project.myblog.repository.HitsRepository;
import project.myblog.utils.DatabaseCleanup;

@Transactional
@Import(TestWebConfig.class)
@SpringBootTest
public class UnitTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private HitsRepository hitsRepository;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
        hitsRepository.flushAll();
    }
}
