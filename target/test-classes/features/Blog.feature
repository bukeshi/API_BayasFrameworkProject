Feature: Mock Blog

  Scenario: Leave a comment under a post
    #/users
    When the following user has been created:
      | name  | email             | age |
      | James | james@belucci.com | 39  |
    Then status code 200 is returned
    #/users/:userID/posts
    When the following post has been created for the user
      | title    | description    |
      | my title | my description |
    Then status code 200 is returned
    # /users/:userID/posts/:postId/ comments
    When the following comment has been left under a post
      | comment    |
      | my comment |
    Then status code 200 is returned
    #Validate the user id
    And the following post has been returned
      | title    | description    | comment    |
      | my title | my description | my comment |