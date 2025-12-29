Feature: Provider Event Type Management
  As a provider
  I want to manage different types of appointment events
  So that clients can book the appropriate type of consultation

  Background:
    Given I am logged in as a provider
    And I navigate to event types settings

  @smoke @provider @event-types
  Scenario: Create new event type
    When I click "Create Event Type"
    And I enter event name "Initial Consultation"
    And I enter description "First-time client consultation"
    And I set duration to "60 minutes"
    And I click save
    Then I should see a success message "Event type created"
    And "Initial Consultation" should appear in my event types list

  @provider @event-types
  Scenario Outline: Set event duration
    When I click "Create Event Type"
    And I enter event name "Quick Check-in"
    And I select duration "<duration>"
    And I click save
    Then the event type should be created with "<duration>" duration

    Examples:
      | duration    |
      |  15 minutes |
      |  30 minutes |
      |  45 minutes |
      |  60 minutes |
      |  90 minutes |
      | 120 minutes |

  @provider @event-types
  Scenario: Configure event locations
    When I click "Create Event Type"
    And I enter event name "Consultation"
    And I enable location "Video Call"
    And I enable location "Phone Call"
    And I enable location "In-Person"
    And I set in-person address to "123 Medical Plaza, Suite 200"
    And I click save
    Then clients should be able to choose from available locations
    And each location should have appropriate booking fields

  @provider @event-types
  Scenario: Set booking rules - minimum notice
    When I click "Create Event Type"
    And I enter event name "Follow-up Appointment"
    And I navigate to booking rules
    And I set minimum notice to "24 hours"
    And I click save
    Then clients should not be able to book within 24 hours
    And available slots should respect the minimum notice period

  @provider @event-types
  Scenario: Set booking rules - buffer times
    When I click "Create Event Type"
    And I enter event name "Consultation"
    And I navigate to booking rules
    And I set buffer time before to "15 minutes"
    And I set buffer time after to "10 minutes"
    And I click save
    Then appointments should have 15-minute buffer before
    And appointments should have 10-minute buffer after

  @provider @event-types
  Scenario: Set booking rules - advance booking limit
    When I click "Create Event Type"
    And I enter event name "Annual Review"
    And I navigate to booking rules
    And I set maximum advance booking to "60 days"
    And I click save
    Then clients should not be able to book beyond 60 days
    And the calendar should only show slots within the limit

  @provider @event-types
  Scenario: Add custom booking fields
    When I click "Create Event Type"
    And I enter event name "New Client Intake"
    And I navigate to custom fields
    And I add field "Reason for Visit" as required text field
    And I add field "Preferred Contact Method" as dropdown with options:
      | Options |
      | Email   |
      | Phone   |
      | Text    |
    And I add field "Special Accommodations" as optional text area
    And I click save
    Then clients should see these fields during booking
    And required fields should be validated

  @provider @event-types
  Scenario: Enable/disable event type
    Given I have an event type "Summer Special"
    When I click toggle status on "Summer Special"
    Then the event type should be disabled
    And clients should not see it in booking options
    When I click toggle status again
    Then the event type should be enabled
    And clients should see it in booking options

  @provider @event-types
  Scenario: Set confirmation requirements
    When I click "Create Event Type"
    And I enter event name "Initial Consultation"
    And I navigate to confirmation settings
    And I enable "Require manual confirmation"
    And I enable "Send confirmation email to client"
    And I enable "Send confirmation email to provider"
    And I click save
    Then bookings should require my manual confirmation
    And both parties should receive confirmation emails

  @provider @event-types
  Scenario: Configure event color
    When I click "Create Event Type"
    And I enter event name "Follow-up"
    And I select color "Blue"
    And I click save
    Then the event should appear in "Blue" on my calendar
    And all "Follow-up" appointments should use this color

  @provider @event-types
  Scenario: Edit event type
    Given I have an event type "Consultation"
    When I click edit on "Consultation"
    And I change duration from "30 minutes" to "45 minutes"
    And I update description
    And I click save
    Then I should see a success message
    And the changes should be applied
    And future bookings should use new settings

  @provider @event-types
  Scenario: Delete event type
    Given I have an event type "Deprecated Service"
    And there are no upcoming appointments for this type
    When I click delete on "Deprecated Service"
    And I confirm the deletion
    Then I should see a success message "Event type deleted"
    And it should be removed from the list

  @provider @event-types
  Scenario: Copy booking link
    Given I have an event type "Initial Consultation"
    When I click "Copy Link" on "Initial Consultation"
    Then the booking link should be copied to clipboard
    And I should see a confirmation "Link copied"
    And the link should be shareable with clients

  @provider @event-types
  Scenario: Set price for event type
    When I click "Create Event Type"
    And I enter event name "Premium Consultation"
    And I navigate to pricing
    And I set price to "$200"
    And I enable payment collection
    And I click save
    Then clients should see the price during booking
    And payment should be required to complete booking

  @provider @event-types @validation
  Scenario: Prevent deletion of event type with upcoming appointments
    Given I have an event type "Consultation"
    And there are upcoming appointments for this type
    When I try to delete "Consultation"
    Then I should see an error message "Cannot delete event type with upcoming appointments"
    And the event type should not be deleted
