package org.ahassignement.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:test_reports/cucumber_reports/cucumber_reports.html",
                "json:test_reports/json_reports/cucumber.json",
                "junit:test_reports/xml_report/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",

        },
        features = "@target/failed_tests.txt",
        glue = {"org.ahassignement.stepDefinitions"}
)
public class FailedTestCaseRunner {
}
