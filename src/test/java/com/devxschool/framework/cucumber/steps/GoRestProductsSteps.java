package com.devxschool.framework.cucumber.steps;

import com.devxschool.framework.api.pojos.Product;
import com.devxschool.framework.api.pojos.ProductResponseObject;
import com.devxschool.framework.cucumber.steps.common.CommonData;
import com.devxschool.framework.utilities.ObjectConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.List;

public class GoRestProductsSteps {

    private CommonData commonData;
    private int productId;
    private int bodyStatCode;

    public GoRestProductsSteps(CommonData commonData) {
        this.commonData = commonData;
    }

    @Given("^base url is \"([^\"]*)\"$")
    public void base_url_is(String arg1)  {
        RestAssured.baseURI = "https://gorest.co.in/public-api";
    }

    @When("^following product is created$")
    public void following_product_is_created(List<Product> requestedProduct)  {
        RequestSpecification rs =getHeadersWithKey();
        rs.body(requestedProduct.get(0));

        commonData.response = rs.post("/products");
        productId = commonData.response.jsonPath().getInt("data.id");
        commonData.response.prettyPrint();

    }

    @Then("^following product response is returned$")
    public void following_product_response_is_returned(List<Product> expectedProductResponse) throws JsonProcessingException {
        ProductResponseObject productResponseObject =
                ObjectConverter.convertJsonObjectToJavaObject(commonData.response.body().asString(),ProductResponseObject.class);
        MatcherAssert.assertThat(productResponseObject.getProduct().getName(), Matchers.equalTo(expectedProductResponse.get(0).getName()));
        MatcherAssert.assertThat(productResponseObject.getProduct().getPrice(),Matchers.equalTo(expectedProductResponse.get(0).getPrice()));
    }

    @When("^the product is deleted$")
    public void the_product_is_deleted()  {
       RequestSpecification rs = getHeadersWithKey();
       commonData.response = rs.pathParam("id",productId).delete("/products/{id}");


    }

    @When("^the product is requested$")
    public void the_product_is_requested()  {
        RequestSpecification rs = getHeadersWithKey();
        commonData.response =rs.pathParam("id",productId).get("/products/{id}");
        JsonPath jsonPath = commonData.response.jsonPath();
        bodyStatCode = jsonPath.getInt("code");
        System.out.println("body stat code is" + bodyStatCode);

    }
    private RequestSpecification getHeadersWithKey(){
        RequestSpecification rs = RestAssured.given();
        rs.headers("Authorization","Bearer 25036c6992464cd93b455d4d88827efb15d1b28858d01ad03ad7b8c3c2260b3f");
        rs.contentType(ContentType.JSON);
        rs.accept(ContentType.JSON);
        return rs;
    }


    @Then("^bodyStatCode (\\d+) is returned$")
    public void bodystatcodeIsReturned(int expectedBodyStatCode) {
        MatcherAssert.assertThat(bodyStatCode,Matchers.equalTo(expectedBodyStatCode));
        System.out.println(bodyStatCode);
        System.out.println(expectedBodyStatCode);
    }
}
