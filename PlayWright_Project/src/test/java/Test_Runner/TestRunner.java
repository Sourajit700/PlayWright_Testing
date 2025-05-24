package Test_Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/main/resources/Web.features",     // path to .feature files
    glue = {"Wbeb.Step_Defination"},                             // package with step definitions
    plugin = {"pretty", "html:target/cucumber-report.html"}, // reports
    monochrome = true,
    dryRun =false,
    tags="@tag01"
)
public class TestRunner {

}
