Feature: Provider Signup
  As a new provider
  I want to sign up for the MTOmics platform
  So that I can start managing my clients

  Background:
    Given the provider signup page is loaded

  @smoke @provider @signup
  Scenario: Complete signup with invitation link
    Given I have received a provider invitation link
    When I click on the invitation link
    And I fill in the signup form with valid details
    And I accept the terms and conditions
    And I click the signup button
    Then I should see a success message
    And I should receive a verification email

  @provider @signup
  Scenario: Signup with valid details
    When I enter first name "John"
    And I enter last name "Doe"
    And I enter email "john.doe@example.com"
    And I enter password "SecurePass123!"
    And I confirm password "SecurePass123!"
    And I accept the terms and conditions
    And I click the signup button
    Then I should see a success message
    And I should be redirected to email verification page

  @provider @signup
  Scenario: Email verification
    Given I have signed up with email "john.doe@example.com"
    When I open the verification email
    And I click on the verification link
    Then I should see email verified message
    And I should be redirected to the provider dashboard

  @provider @signup @validation
  Scenario Outline: Password requirements validation
    When I enter password "<password>"
    And I confirm password "<password>"
    Then I should see password validation message "<message>"

    Examples:
      | password      | message                                    |
      | short         | Password must be at least 8 characters     |
      | nouppercasechar | Password must contain an uppercase letter |
      | NOLOWERCASE1  | Password must contain a lowercase letter   |
      | NoNumber!     | Password must contain a number             |
      | NoSpecial123  | Password must contain a special character  |
      | ValidPass1!   | Password meets all requirements            |

  @provider @signup @negative
  Scenario: Signup with existing email
    Given a provider account exists with email "existing@example.com"
    When I enter email "existing@example.com"
    And I fill in other signup details
    And I click the signup button
    Then I should see an error message "Email already registered"
    And I should remain on the signup page

  @provider @signup @security
  Scenario: MFA setup during signup
    Given I have completed the basic signup
    When I am prompted to set up MFA
    And I scan the QR code with my authenticator app
    And I enter the verification code
    Then MFA should be enabled for my account
    And I should see a success message
