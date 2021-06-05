@GoRestProducts
Feature: Testing Go Rest Product API
  @CreateProduct
  Scenario: Create Product
    Given base url is "https://gorest.co.in/public-api"
    When following product is created
    |name|description|image|price|status|
    |MacBook Pro 16|laptop|https://google.com/macpro.png|2999.99|active|
    Then status code 200 is returned
    And following product response is returned
      |name|description|image|price|status|
      |MacBook Pro 16|laptop|https://google.com/macpro.png|2999.99|active|
    When the product is deleted
    Then status code 200 is returned
    When the product is requested
    Then bodyStatCode 404 is returned
