import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  plugin = {"json:target/html-reports/cucumber.json", "html:target/cucumber"},
  glue = "steps",
  features = "classpath:features",
  tags = "@Regression"
)
public class RunCukesTest {
}
