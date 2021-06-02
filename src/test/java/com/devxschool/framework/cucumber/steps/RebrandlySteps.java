package com.devxschool.framework.cucumber.steps;

import com.devxschool.framework.api.pojos.RebrandlyLink;
import com.devxschool.framework.utilities.ObjectConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RebrandlySteps {
    private Response response;
    private String linkId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://api.rebrandly.com";
    }

    @Given("^base URL \"([^\"]*)\"$")
    public void base_URL(String baseURL) {
        RestAssured.baseURI = baseURL;
    }

    @When("^all links are requested$")
    public void all_links_are_requested() {
        RequestSpecification rs = setUpHeaders();
        response = rs.get("/v1/links/");

    }

    @When("^all links are requested with following query params$")
    public void all_links_are_requested_with_following_query_params(List<Map<String, String>> queryParams) {
        RequestSpecification rs = setUpHeaders();
        response = rs

//                .queryParam("limit", queryParams.get(0).get("limit"))
//                .queryParam("domainID",queryParams.get(0).get("domainID"))
//                instead of chaining we can send whole map of params
                .queryParams(queryParams.get(0))
                .get("/v1/links/");

    }

    @When("^all links are requested with following orderDir params$")
    public void all_links_are_requested_with_following_orderDir_params(List<Map<String, String>> orderDirParams) {
        RequestSpecification rs = setUpHeaders();

        response = rs.queryParam("orderDir", orderDirParams.get(0).get("orderDir")).get("/v1/links/");
    }

    @When("^all links are requested with fullName query params$")
    public void all_links_are_requested_with_fullName_query_params(List<Map<String, String>> fullNameQuery) {
        RequestSpecification rs = setUpHeaders();
        response = rs.queryParam("domain.fullName", fullNameQuery.get(0).get("domain.fullName")).get("/v1/links");

    }

    @Then("^status code (\\d+) is returned$")
    public void status_code_is_returned(int expectedStatusCode) {

        MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(expectedStatusCode));
    }

    @Then("^only (\\d+) link is returned$")
    public void only_link_is_returned(int size) throws JsonProcessingException {
/*      regular way / to simplify  we will use helper Generic method instead

        ObjectMapper objectMapper = new ObjectMapper();
        List<RebrandlyLink> linkResponses =
                Arrays.asList(objectMapper.readValue(response.body().asString(), RebrandlyLink[].class));
        */
        List<RebrandlyLink> linkResponses = ObjectConverter.convertJsonArrayToListOfObjects(response.body().asString(),RebrandlyLink[].class);
        MatcherAssert.assertThat(linkResponses.size(), Matchers.equalTo(size));

    }

    @Then("^first link title is \"([^\"]*)\";$")
    public void first_link_title_is(String expectedTitle) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<RebrandlyLink> linkResponses =
                Arrays.asList(objectMapper.readValue(response.body().asString(), RebrandlyLink[].class));
        MatcherAssert.assertThat(linkResponses.get(0).getTitle(), Matchers.equalToIgnoringCase(expectedTitle));
    }

    @When("^all links are requested with creator\\.fullName query params$")
    public void all_links_are_requested_with_creator_fullName_query_params(List<Map<String, String>> params) {
        RequestSpecification rs = setUpHeaders();

        response = rs.queryParam("creator.fullName", params.get(0).get("creator.fullName")).get("/v1/links");

    }

    @Then("^first link creator\\.fullName is \"([^\"]*)\";$")
    public void first_link_creator_fullName_is(String expectedFullName) throws JsonProcessingException {

        List<RebrandlyLink> rebrandlyLinkList = ObjectConverter.convertJsonArrayToListOfObjects(response.body().asString(), RebrandlyLink[].class);

        MatcherAssert.assertThat(rebrandlyLinkList.get(0).getCreator().getFullName(), Matchers.is(expectedFullName));
    }

    @Then("^domainId is \"([^\"]*)\"$")
    public void domainid_is(String expectedDomainId) throws Throwable {
        List<RebrandlyLink> rebrandlyLinkList =
                ObjectConverter.convertJsonArrayToListOfObjects(response.body().asString(), RebrandlyLink[].class);

        MatcherAssert.assertThat(rebrandlyLinkList.get(0).getDomainId(), Matchers.equalTo(expectedDomainId));
    }

    @Then("^verify that (\\d+) links have been returned with domainId \"([^\"]*)\"$")
    public void verify_that_links_have_been_returned_with_domainId(int expectedNumberOfLinks, String expectedDomainId) throws JsonProcessingException {
       /*
        ObjectMapper objectMapper = new ObjectMapper();
        List<RebrandlyLink> linkResponseList = Arrays.asList(objectMapper.readValue(response.body().asString(), RebrandlyLink[].class));
        */

        List<RebrandlyLink> linkResponseList = ObjectConverter.convertJsonArrayToListOfObjects(response.body().asString(),RebrandlyLink[].class);

        MatcherAssert.assertThat(linkResponseList.size(), Matchers.is(expectedNumberOfLinks));
        for (RebrandlyLink linkResponse : linkResponseList) {
            MatcherAssert.assertThat(linkResponse.getDomainId(), Matchers.is(expectedDomainId));
        }

    }

    @When("^following link is created$")
    public void following_link_is_created(List<Map<String, String>> linkRequest) {
        RebrandlyLink rebrandlyLinkRequest = new RebrandlyLink();
        rebrandlyLinkRequest.setDestination(linkRequest.get(0).get("destination"));


        RequestSpecification rs = setUpHeaders();
        rs.body(rebrandlyLinkRequest);

        response = rs.post("/v1/links");
        linkId = response.getBody().jsonPath().getString("id");


    }

    @Then("^following link has been returned$")
    public void following_link_has_been_created(List<Map<String, String>> expectedLinkResponse) throws JsonProcessingException {
/*
        ObjectMapper objectMapper = new ObjectMapper();
        RebrandlyLink rebrandlyLinkResponse = objectMapper.readValue(response.body().asString(), RebrandlyLink.class);
        */
        RebrandlyLink rebrandlyLinkResponse = ObjectConverter.convertJsonObjectToJavaObject(response.body().asString(),RebrandlyLink.class);
        linkId = rebrandlyLinkResponse.getId();

        MatcherAssert.assertThat(rebrandlyLinkResponse.getDestination(), Matchers.equalTo(expectedLinkResponse.get(0).get("destination")));

    }

    @When("^link details has been requested$")
    public void getLindDetails() {
        RequestSpecification rs = setUpHeaders();

        response = rs
                .pathParam("id", linkId)
                .get("/v1/links/{id}");

    }
    @When("^the link with id \"([^\"]*)\" is updated with following data$")
    public void the_link_with_id_is_updated_with_following_data(String id, List<Map<String,String>> linkRequestBody)  {
            RequestSpecification rs = setUpHeaders();
            RebrandlyLink rebrandlyLinkBody = new RebrandlyLink();
            rebrandlyLinkBody.setDestination(linkRequestBody.get(0).get("destination"));

            rs.body(rebrandlyLinkBody);

            response = rs.pathParam("id",id).post("/v1/links/{id}");
    }

    private RequestSpecification setUpHeaders() {
        RequestSpecification requestSpec = RestAssured.given();

        requestSpec.headers("apikey", "b6d12716f5ac4af881a0b07219e6807b");
        requestSpec.contentType(ContentType.JSON);
        requestSpec.accept(ContentType.JSON);

        return requestSpec;
    }


    @When("^the link has been deleted$")
    public void deleteLink() {
        RequestSpecification rs = setUpHeaders();
        response = rs
                .pathParam("id",linkId)
                .delete("/v1/links/{id}");

    }
//    make sure import cucumber afterhook
    @After
    public void tearDown(){
        if(linkId != null){
            deleteLink();
        }
    }
}
