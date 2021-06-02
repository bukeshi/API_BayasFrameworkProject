@GoRest
Feature: Testing Go Rest API functionality

  @getAllUsers
  Scenario: Get All Users
    When all users are requested
    Then status code returned is 200
    And 20 users are returned

  @CreateUser
  Scenario: Create User
    When following user is created
      | name        | email                | gender | status |
      | Bruce Wayne | brucewayne@email.com | Male   | Active |
    Then status code returned is 200
    And following user response is returned
      | name        | email                | gender | status |
      | Bruce Wayne | brucewayne@email.com | Male   | Active |

  @DeleteUser
  Scenario: Delete User
    When following user is created
      | name         | email                  | gender | status |
      | Bruce Delete | bruce_delete@email.com | Male   | Active |
    Then status code returned is 200
    And following user response is returned
      | name         | email                  | gender | status |
      | Bruce Delete | bruce_delete@email.com | Male   | Active |
    When the user is deleted
    Then status code returned is 200
    When the user id is requested
    Then status code returned is 200
