import com.scoreboard.services.implementation.Sample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import setup.BaseTest;


public class SampleTest extends BaseTest {
    @Autowired
    Sample sample;

    @Test
    @Order(1)
    public void test_sample_success(){
        sample.test();
    }
}