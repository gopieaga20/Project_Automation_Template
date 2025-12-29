Feature: Provider Proxy Client Invitation
  As a provider
  I want to invite proxy clients (caregivers/family members)
  So that they can manage health records on behalf of the actual client

  Background:
    Given I am logged in as a provider
    And I have an existing client "John Doe"
    And I navigate to the client's profile

  @provider @proxy-invitation
  Scenario: Invite proxy client
    When I click the "Add Proxy" button
    And I enter proxy first name "Mary"
    And I enter proxy last name "Doe"
    And I enter proxy email "mary.doe@example.com"
    And I enter proxy relationship "Spouse"
    And I click send invitation
    Then I should see a success message "Proxy invitation sent"
    And the proxy should receive an invitation email

  @provider @proxy-invitation
  Scenario: Set proxy relationship
    When I click the "Add Proxy" button
    And I enter proxy details
    And I select relationship from dropdown:
      | Relationship Options |
      | Spouse               |
      | Parent               |
      | Child                |
      | Sibling              |
      | Legal Guardian       |
      | Caregiver            |
      | Other                |
    And I select "Parent"
    And I click send invitation
    Then the proxy relationship should be set to "Parent"

  @provider @proxy-invitation
  Scenario: Assign permissions to proxy
    When I click the "Add Proxy" button
    And I enter proxy details
    And I configure proxy permissions:
      | Permission                 | Granted |
      | View Medical Records       | Yes     |
      | Upload Documents           | Yes     |
      | Schedule Appointments      | Yes     |
      | View Lab Results           | Yes     |
      | Communicate with Provider  | Yes     |
      | Modify Client Profile      | No      |
      | Access Billing Information | No      |
    And I click send invitation
    Then the proxy should have the specified permissions
    And the permissions should be reflected in the invitation email

  @provider @proxy-invitation
  Scenario: Verify proxy invitation email
    Given I have sent a proxy invitation to "mary.doe@example.com"
    When the proxy opens the invitation email
    Then the email should contain:
      | Content                        |
      | Provider name                  |
      | Client name they are proxy for |
      | Relationship type              |
      | Granted permissions list       |
      | Invitation acceptance link     |
      | Expiration date                |

  @provider @proxy-invitation
  Scenario: Manage proxy access
    Given a proxy "Mary Doe" has accepted the invitation
    When I navigate to the client's profile
    And I view the proxy list
    Then I should see "Mary Doe" listed as a proxy
    And I should be able to:
      | Action                  |
      | Edit proxy permissions  |
      | Remove proxy access     |
      | View proxy activity log |

  @provider @proxy-invitation
  Scenario: Edit proxy permissions
    Given a proxy "Mary Doe" exists for client "John Doe"
    When I click edit proxy permissions
    And I revoke "Schedule Appointments" permission
    And I grant "Access Billing Information" permission
    And I click save changes
    Then I should see a success message
    And the proxy permissions should be updated
    And the proxy should receive a notification about permission changes

  @provider @proxy-invitation
  Scenario: Remove proxy access
    Given a proxy "Mary Doe" exists for client "John Doe"
    When I click remove proxy
    And I confirm the removal
    Then I should see a success message "Proxy access removed"
    And the proxy should no longer have access to the client's records
    And the proxy should receive a notification about access removal
