package com.devxschool.framework.cucumber.steps;


import com.devxschool.framework.api.pojos.User;
import com.devxschool.framework.api.pojos.UserResponseList;
import com.devxschool.framework.api.pojos.UserResponseObject;
import com.devxschool.framework.utilities.ObjectConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.List;

public class GorestusersSteps {
    private Response response;
    private int userId;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
    }

    @When("^all users are requested$")
    public void all_users_are_requested() {
        RequestSpecification rs = getHeadersNoKey();
        response =rs.get("/users");
    }

    @Then("^status code returned is (\\d+)$")
    public void status_code_returned_is(int expectedStatusCode) {
        MatcherAssert.assertThat(response.getStatusCode(),Matchers.is(expectedStatusCode));
    }

    @Then("^(\\d+) users are returned$")
    public void users_are_returned(int expectedSize) throws JsonProcessingException {
            UserResponseList userResponseList = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserResponseList.class);
            MatcherAssert.assertThat(userResponseList.getUserList().size(),Matchers.is(expectedSize));
    }
    @When("^following user is created$")
    public void following_user_is_created(List<User> userRequest)  {
        RequestSpecification rs = getHeadersWithKey();
        rs.body(userRequest.get(0));

        response = rs.post("/users");
        response.prettyPrint();
        userId = response.getBody().jsonPath().getInt("data.id");
        System.out.println(userId);
    }

    @Then("^following user response is returned$")
    public void following_user_response_is_returned( List<User> expectedUserResponse) throws JsonProcessingException {
        UserResponseObject actualUserResponse =
                ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),UserResponseObject.class);

        MatcherAssert.assertThat(actualUserResponse.getUser().getName(),Matchers.is(expectedUserResponse.get(0).getName()));
        MatcherAssert.assertThat(actualUserResponse.getUser().getStatus(),Matchers.is(expectedUserResponse.get(0).getStatus()));
        MatcherAssert.assertThat(actualUserResponse.getUser().getGender(),Matchers.is(expectedUserResponse.get(0).getGender()));
        MatcherAssert.assertThat(actualUserResponse.getUser().getEmail(),Matchers.is(expectedUserResponse.get(0).getEmail()));

        System.out.println(actualUserResponse.getUser());
    }

    @When("^the user is deleted$")
    public void the_user_is_deleted()  {
        RequestSpecification rs = getHeadersWithKey();
        response = rs
                .pathParam("id",userId)
                .delete("/users/{id}");
    }

    @When("^the user id is requested$")
    public void the_user_id_is_requested()  {
        RequestSpecification rs=getHeadersNoKey();

        response = rs
                .pathParam("id",userId)
                .get("/users/{id}");
    }

    private  RequestSpecification getHeadersWithKey(){
        RequestSpecification rs = RestAssured.given();
        rs.headers("Authorization","Bearer 25036c6992464cd93b455d4d88827efb15d1b28858d01ad03ad7b8c3c2260b3f");
        rs.contentType(ContentType.JSON);
        rs.accept(ContentType.JSON);
        return  rs;
    }
    private RequestSpecification getHeadersNoKey(){
        RequestSpecification rs = RestAssured.given();
        rs.accept(ContentType.JSON);

        return rs;
    }


}
