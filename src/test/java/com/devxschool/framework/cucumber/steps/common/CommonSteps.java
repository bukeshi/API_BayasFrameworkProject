package com.devxschool.framework.cucumber.steps.common;

import cucumber.api.java.en.Then;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class CommonSteps {
    private CommonData commonData;

    public CommonSteps(CommonData commonData) {
        this.commonData = commonData;
    }

    @Then("^status code (\\d+) is returned$")
    public void status_code_is_returned(int expectedStatusCode) {

        MatcherAssert.assertThat(commonData.response.getStatusCode(), Matchers.equalTo(expectedStatusCode));
    }
}
