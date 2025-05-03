import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import setup.BaseTest;

public class SampleTest extends BaseTest {
    @InjectMocks
    Sample sample;

    @Test
    public void test_sample_success(){
        sample.test();
    }
}