@Rebrandly
Feature: Rebrandly Links
  As a user of Rebrandly APIs
  I would like to work with links (CRUD operations)
  so I can fill my page with links

  @GetAllLinks
  Scenario: Get all links
    When all links are requested
    Then status code 200 is returned

  @getAllLinksWithLimitQueryParams
  Scenario: Get all links with limit query param
    When all links are requested with following query params
      | limit |
      | 2     |
    Then status code 200 is returned
    And only 2 link is returned

  @GetAllLinksWithDomainFullName
  Scenario: Get all links with domain.fullName
    When all links are requested with fullName query params
      | domain.fullName |
      | rebrand.ly      |
    Then status code 200 is returned
    And only 25 link is returned

  Scenario: Get all links ordered in ascending order
    When all links are requested with following orderDir params
      | orderDir |
      | asc      |
    Then status code 200 is returned
    And first link title is "amazon";

  Scenario: Get all links with creator.fullName
    When all links are requested with creator.fullName query params
      | creator.fullName |
      | Bulat Kan        |
    Then status code 200 is returned
    And first link creator.fullName is "Bulat Kan";

  Scenario: Get all links with domainId query params
    When all links are requested with following query params
      | domainId                         |
      | 8f104cc5b6ee4a4ba7897b06ac2ddcfb |
    Then status code 200 is returned
    And domainId is "8f104cc5b6ee4a4ba7897b06ac2ddcfb"

  Scenario: Get all links with multiple query params
    When all links are requested with following query params
      | domainId                         | limit |
      | 8f104cc5b6ee4a4ba7897b06ac2ddcfb | 3     |
    Then status code 200 is returned
    And verify that 3 links have been returned with domainId "8f104cc5b6ee4a4ba7897b06ac2ddcfb"

  @createLink
  Scenario: Create a link
    When following link is created
      | destination         |
      | https://youtube.com |
    Then status code 200 is returned
    And following link has been returned
      | destination         |
      | https://youtube.com |

  @createLinkAndGetLinkDetails
  Scenario: create link and get link details
    When following link is created
      | destination         |
      | https://youtube.com |
    Then status code 200 is returned
    When link details has been requested
    Then status code 200 is returned
    And following link has been returned
      | destination         |
      | https://youtube.com |

  @updateLink
  Scenario: update a link
    When the link with id "79996a937fb14634a0e06df92a55e0e5" is updated with following data
      | destination           |
      | https://love4free.com |
    Then status code 200 is returned
    And following link has been returned
      | destination           |
      | https://love4free.com |

  @deleteLink
  Scenario: delete a link
    When following link is created
      | destination       |
      | https://myweb.com |
    Then status code 200 is returned
    When the link has been deleted
    Then status code 200 is returned
      