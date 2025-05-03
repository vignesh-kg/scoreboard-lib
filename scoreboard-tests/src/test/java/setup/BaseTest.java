package setup;

import com.scoreboard.config.ServicesConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = {ServicesConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public abstract class BaseTest {
}
