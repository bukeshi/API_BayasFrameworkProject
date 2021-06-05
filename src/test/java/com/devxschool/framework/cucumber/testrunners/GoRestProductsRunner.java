package com.devxschool.framework.cucumber.testrunners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/GoRestProducts.feature"
        },
        glue = {
                "com.devxschool.framework.cucumber.steps"
        },
        tags = {
                " @CreateProduct"
        },
        dryRun = false
)
public class GoRestProductsRunner {
}
