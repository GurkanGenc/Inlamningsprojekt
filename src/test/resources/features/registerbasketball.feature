Feature: User registration on Basketball England

  Scenario: Successful user registration
    Given I navigate to the Basketball England registration page
    When I enter valid registration details
    * I accept the terms and conditions
    * I accept the age condition
    * I accept the code of ethics and conduct
    * I submit the registration form
    Then I should see a confirmation or be redirected

  Scenario Outline: Unsuccessful user registration with missing last name
    Given I navigate to the Basketball England registration page
    When I enter invalid registration details last name "<LastName>"
    * I accept the terms and conditions
    * I accept the age condition
    * I accept the code of ethics and conduct
    * I submit the registration form
    Then I should see a warning message shows "<ExpectedWarning>"
    Examples:
      | LastName | ExpectedWarning                                           |
      |          | Last Name is required                                     |
      | 123      | Invalid Value - Allowed characters: A-Z, space and '/\&.- |
      | Genc@    | Invalid Value - Allowed characters: A-Z, space and '/\&.- |
      | <script> | Invalid Value - Allowed characters: A-Z, space and '/\&.- |

  Scenario: Unsuccessful user registration with wrong confirmation password
    Given I navigate to the Basketball England registration page
    When I enter invalid registration details confirmation password
    * I accept the terms and conditions with a delay
    * I accept the age condition
    * I accept the code of ethics and conduct
    * I submit the registration form
    Then I should see a warning message for wrong confirmation password

  Scenario: Unsuccessful user registration without confirming terms and conditions
    Given I navigate to the Basketball England registration page
    When I enter valid registration details
    * I do not accept term and conditions
    * I accept the age condition
    * I accept the code of ethics and conduct
    * I submit the registration form
    Then I should see a warning message for term and conditions