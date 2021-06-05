package com.devxschool.framework.cucumber.steps;

import io.restassured.RestAssured;
import org.junit.Before;

public class BlogSteps {
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://5f629b9c67e195001652f0a4.mockapi.io/api/v1/users/1/posts";

    }
}
