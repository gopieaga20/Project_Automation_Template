@admin @invite @manager
Feature: Admin Invite Manager
  As an admin user
  I want to invite managers to the MTOmics platform
  So that they can help manage the system

  Background:
    Given I navigate to the MTOmics login page
    When I enter email "ajith.r+admin@spritle.com"
    And I enter password "Qwerty@123"
    And I click the sign in button
    Then I should be redirected to the users page
    And I should see the user management page
    And I click the new invite button

  @smoke @positive
  Scenario: Successfully invite a manager with valid details
    When I enter manager email "gopinath.e+managerA1@spritle.com"
    And I enter manager first name "Lakshmi"
    And I enter manager last name "Suresh"
    And I select role "Manager"
    And I should see the email preview with manager role details
    And I click send invitation button
    Then I should see invitation sent success message
    And the manager invitation should appear in pending invitations

  @negative
  Scenario: Invite manager with duplicate email
    Given I enter manager email "gopinath.e+pa1@spritle.com"
    When I enter manager first name "Bob"
    Then I should see error message "User already exist"

  @negative
  Scenario: Invite manager with already sent invitation
    Given I enter manager email "gopinath.e+manager1@spritle.com"
    When I enter manager first name "David"
    Then I should see error message "Invitation already sent"

  @positive @inviteManager
  Scenario Outline: Invite managers with different names
    When I enter manager email "<email>"
    And I enter manager first name "<firstName>"
    And I enter manager last name "<lastName>"
    And I select role "Manager"
    And I should see the email preview with manager role details
    And I click send invitation button
    Then I should see invitation sent success message

    Examples:
      | email                            | firstName | lastName |
      | gopinath.e+manager8@spritle.com  | Lakshmi   | Suresh   |
      | gopinath.e+manager9@spritle.com  | Ajith     | Prakash  |
      | gopinath.e+manager10@spritle.com | Kevin     | Elwin    |

  @positive
  Scenario: Cancel manager invitation
    When I enter manager email "gopinath.e+manager11@spritle.com"
    And I enter manager first name "Cancel"
    And I enter manager last name "Test"
    And I close the invite dialog
    Then the invitation should not be sent
    And I should return to user management page
