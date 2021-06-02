package com.devxschool.framework.cucumber.testrunners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/gorestusers.feature"
        },
        glue = {
                "com.devxschool.framework.cucumber.steps"
        },
        tags = {
                " @DeleteUser"
        },
        dryRun = false
)
public class GorestTest {
}
