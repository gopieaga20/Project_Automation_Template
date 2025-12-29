Feature: Provider Profile Management
  As a provider
  I want to manage my profile information
  So that my clients can see accurate information about me

  Background:
    Given I am logged in as a provider
    And I navigate to my profile page

  @smoke @provider @profile
  Scenario: Update personal information
    When I click the edit profile button
    And I update first name to "Jane"
    And I update last name to "Smith"
    And I update title to "Dr."
    And I click save changes
    Then I should see a success message "Profile updated successfully"
    And my profile should display "Dr. Jane Smith"

  @provider @profile
  Scenario: Update contact details
    When I click the edit contact button
    And I update phone number to "+1-555-123-4567"
    And I update email to "jane.smith@newdomain.com"
    And I click save changes
    Then I should see a success message
    And my contact details should be updated

  @provider @profile
  Scenario: Update professional credentials
    When I click the edit credentials button
    And I update license number to "MD123456"
    And I update specialty to "Functional Medicine"
    And I update years of experience to "15"
    And I add certification "Board Certified in Internal Medicine"
    And I click save changes
    Then I should see a success message
    And my credentials should be displayed on my profile

  @provider @profile
  Scenario: Upload profile picture
    When I click the profile picture area
    And I select an image file "profile.jpg"
    And I click upload
    Then I should see a success message
    And my profile picture should be updated

  @provider @profile @security
  Scenario: Change password
    When I navigate to security settings
    And I click change password
    And I enter current password "OldPass123!"
    And I enter new password "NewSecurePass456!"
    And I confirm new password "NewSecurePass456!"
    And I click update password
    Then I should see a success message "Password changed successfully"
    And I should be able to login with the new password

  @provider @profile @security
  Scenario: Enable MFA
    When I navigate to security settings
    And I click enable MFA
    And I scan the QR code
    And I enter the verification code
    Then MFA should be enabled
    And I should see a success message

  @provider @profile @security
  Scenario: Disable MFA
    Given MFA is enabled for my account
    When I navigate to security settings
    And I click disable MFA
    And I enter my password for confirmation
    And I enter the current MFA code
    Then MFA should be disabled
    And I should see a success message

  @provider @profile
  Scenario: Update timezone
    When I navigate to preferences
    And I select timezone "America/New_York"
    And I click save preferences
    Then I should see a success message
    And my timezone should be set to "America/New_York"
    And all times should be displayed in the selected timezone
