package cucumber.runners;

import cucumber.helper.GenerateReport;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;

@CucumberOptions(
        features = "src/test/resources", // lokasi file .feature
        glue = "cucumber.definitions",   // lokasi step definitions
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-reports.html"
        },
        monochrome = true
)
public class CucumberRunners extends AbstractTestNGCucumberTests {

    @AfterSuite
    public void generateReport() {
        GenerateReport.generateReport();
    }
}
