@admin1 @login
Feature: Admin Login
  As an admin user
  I want to login to the MTOmics platform
  So that I can manage users and system settings

  Background:
    Given I navigate to the MTOmics login page

  @smoke @positive
  Scenario: Successful admin login with valid credentials
    When I enter email "ajith.r+admin@spritle.com"
    And I enter password "Qwerty@123"
    And I click the sign in button
    Then I should be redirected to the users page
    And I should see the user management page

  @negative
  Scenario: Failed login with invalid email
    When I enter email "invalid@mtomics.com"
    And I enter password "WrongPassword"
    And I click the sign in button
    Then I should see an error message "Incorrect email or password"

  @negative
  Scenario: Failed login with invalid password
    When I enter email "ajith.r+admin@spritle.com"
    And I enter password "WrongPassword123"
    And I click the sign in button
    Then I should see an error message "Incorrect email or password"

  @negative
  Scenario: Failed login with empty credentials
    When I click the sign in button
    Then I should remain on the login page

  @positive @toggle
  Scenario: Password visibility toggle
    When I enter password "Qwerty@123"
    And I click the toggle password visibility button
    Then the password should be visible

  @positive
  Scenario: Navigate to forgot password page
    When I click the forgot password button
    Then I should be redirected to the forgot password page

  @positive
  Scenario: Navigate to create account page
    When I click the create account button
    Then I should be redirected to the sign up page
