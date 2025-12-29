Feature: Provider Client Invitation
  As a provider
  I want to invite clients to the platform
  So that I can manage their health records and appointments

  Background:
    Given I am logged in as a provider
    And I navigate to the clients page

  @smoke @provider @client-invitation
  Scenario: Invite client with valid email
    When I click the "Invite Client" button
    And I enter client email "client@example.com"
    And I click send invitation
    Then I should see a success message "Invitation sent successfully"
    And the client should receive an invitation email
    And the invitation should appear in pending invitations list

  @provider @client-invitation
  Scenario: Invite client with first and last name
    When I click the "Invite Client" button
    And I enter client first name "Sarah"
    And I enter client last name "Johnson"
    And I enter client email "sarah.johnson@example.com"
    And I click send invitation
    Then I should see a success message
    And the invitation email should be personalized with "Sarah Johnson"

  @provider @client-invitation
  Scenario: Assign initial surveys during invitation
    When I click the "Invite Client" button
    And I enter client email "client@example.com"
    And I select initial survey "Health History Questionnaire"
    And I select initial survey "Lifestyle Assessment"
    And I click send invitation
    Then I should see a success message
    And the client should receive invitation with assigned surveys
    And the surveys should be available upon client signup

  @provider @client-invitation
  Scenario: Invite multiple clients
    When I click the "Invite Client" button
    And I enter multiple client emails:
      | Email               |
      | client1@example.com |
      | client2@example.com |
      | client3@example.com |
    And I click send invitations
    Then I should see a success message "3 invitations sent"
    And all clients should receive invitation emails
    And all invitations should appear in pending list

  @provider @client-invitation
  Scenario: Verify invitation email sent
    Given I have sent an invitation to "client@example.com"
    When I check the invitation status
    Then the status should show "Email Sent"
    And I should see the sent timestamp
    And I should see the invitation link

  @provider @client-invitation
  Scenario: Resend client invitation
    Given I have sent an invitation to "client@example.com" 5 days ago
    And the client has not accepted the invitation
    When I click resend invitation
    Then I should see a confirmation dialog
    And I click confirm resend
    Then I should see a success message "Invitation resent"
    And the client should receive a new invitation email
    And the sent timestamp should be updated

  @provider @client-invitation
  Scenario: View pending invitations
    Given I have sent multiple invitations
    When I navigate to pending invitations
    Then I should see a list of all pending invitations
    And each invitation should display:
      | Field       |
      | Client Name |
      | Email       |
      | Sent Date   |
      | Status      |
      | Actions     |

  @provider @client-invitation @negative
  Scenario: Revoke client invitation
    Given I have sent an invitation to "client@example.com"
    And the invitation is still pending
    When I click revoke invitation
    And I confirm the revocation
    Then I should see a success message "Invitation revoked"
    And the invitation should be removed from pending list
    And the invitation link should become invalid
