package setup;

import com.scoreboard.config.ServicesConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ServicesConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class BaseTest {
}
