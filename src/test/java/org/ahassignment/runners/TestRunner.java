package org.ahassignment.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/test_reports/cucumber_reports/cucumber_reports.html",
                "json:target/test_reports/json_reports/cucumber.json",
                "junit:target/test_reports/xml_report/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failed_tests.txt",

        },
        features = "src/test/resources/features",
        glue = {"org.ahassignment.stepDefinitions"},
        tags = "@test"
)
public class TestRunner {

}