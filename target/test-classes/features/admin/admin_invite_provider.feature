@admin2 @invite @provider
Feature: Admin Invite Provider
  As an admin user
  I want to invite providers to the MTOmics platform
  So that they can start using the system

  Background:
    Given I navigate to the MTOmics login page
    When I enter email "ajith.r+admin@spritle.com"
    And I enter password "Qwerty@123"
    And I click the sign in button
    Then I should be redirected to the users page
    And I should see the user management page
    And I click the new invite button

  @smoke @positive
  Scenario: Successfully invite a provider with valid details
    When I enter provider email "gopinath.e+provider11@spritle.com"
    And I enter provider first name "John"
    And I enter provider last name "Doe"
    And I select role "Provider"
    And I should see the email preview with provider details
    And I click send invitation button
    Then I should see invitation sent success message
    And the invitation should appear in pending invitations

  @negative
  Scenario: Invite provider with duplicate email
    Given I enter provider email "gopinath.e+qp20@spritle.com"
    When I enter provider first name "Jane"
    Then I should see error message "User already exist"

  @negative
  Scenario: Invite provider with invalid email format
    When I enter provider email "gopinath.e+provider2"
    And I enter provider first name "Test"
    And I enter provider last name "User"
    And I select role "Provider"
    And I should see the email preview with provider details
    And I click send invitation button
    Then I should see error message "Invalid email format"

  @negative
  Scenario: Invite provider with missing required fields
    Given I enter provider email "gopinath.e+provider33@spritle.com"
    When I click send invitation button
    Then I should see validation errors for required fields

  @positive
  Scenario: Edit invitation email template
    When I enter provider email "gopinath.e+provider77@spritle.com"
    And I enter provider first name "Custom"
    And I enter provider last name "Email"
    And I select role "Provider"
    And I click edit email template button
    Then I should see the email editor
