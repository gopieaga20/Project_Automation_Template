Feature: Provider Appointment Booking
  As a provider
  I want to book appointments for my clients
  So that I can schedule consultations and follow-ups

  Background:
    Given I am logged in as a provider
    And I have configured my availability
    And I have created event types

  @smoke @provider @appointments
  Scenario: Book appointment for client
    Given I have a client "John Doe"
    When I navigate to appointments
    And I click "Book Appointment"
    And I select client "John Doe"
    And I select event type "Initial Consultation"
    And I select date "2024-02-15"
    And I select time slot "10:00 AM"
    And I click confirm booking
    Then I should see a success message "Appointment booked"
    And the appointment should appear on my calendar

  @provider @appointments
  Scenario: Select event type
    When I click "Book Appointment"
    And I select client "John Doe"
    And I view available event types
    Then I should see all my active event types:
      | Event Type           | Duration |
      | Initial Consultation |   60 min |
      | Follow-up            |   30 min |
      | Lab Review           |   45 min |
    When I select "Initial Consultation"
    Then the booking should be configured for 60 minutes

  @provider @appointments
  Scenario: Choose available time slot
    Given I am booking an appointment for "John Doe"
    And I have selected event type "Follow-up"
    When I select date "2024-02-15"
    Then I should see only available time slots
    And unavailable slots should be disabled
    And booked slots should not be shown
    When I select time slot "02:00 PM"
    Then the slot should be highlighted

  @provider @appointments
  Scenario: Add appointment notes
    Given I am booking an appointment for "John Doe"
    When I fill in all required booking details
    And I click "Add Notes"
    And I enter "Discuss recent lab results and treatment plan"
    And I click confirm booking
    Then the appointment should be saved with the notes
    And I should be able to view the notes in appointment details

  @provider @appointments
  Scenario: Send appointment confirmation
    Given I have booked an appointment for "John Doe"
    When the appointment is confirmed
    Then "John Doe" should receive a confirmation email
    And the email should contain:
      | Information           |
      | Appointment Date      |
      | Appointment Time      |
      | Event Type            |
      | Duration              |
      | Location/Meeting Link |
      | Provider Name         |
      | Calendar Invite       |

  @provider @appointments
  Scenario: View booked appointments
    Given I have multiple appointments booked
    When I navigate to my calendar
    Then I should see all appointments displayed
    And I should be able to view by:
      | View Type |
      | Day       |
      | Week      |
      | Month     |
      | Agenda    |

  @provider @appointments
  Scenario: Filter appointments by client
    Given I have appointments with multiple clients
    When I navigate to appointments list
    And I apply filter for client "John Doe"
    Then I should see only appointments with "John Doe"
    And other appointments should be hidden

  @provider @appointments
  Scenario: Reschedule appointment
    Given I have an appointment with "John Doe" on "2024-02-15" at "10:00 AM"
    When I click on the appointment
    And I click "Reschedule"
    And I select new date "2024-02-20"
    And I select new time "02:00 PM"
    And I click confirm reschedule
    Then I should see a success message "Appointment rescheduled"
    And the client should receive a reschedule notification
    And the calendar should reflect the new time

  @provider @appointments
  Scenario: Cancel appointment
    Given I have an appointment with "John Doe" on "2024-02-15" at "10:00 AM"
    When I click on the appointment
    And I click "Cancel Appointment"
    And I enter cancellation reason "Provider emergency"
    And I confirm the cancellation
    Then I should see a success message "Appointment cancelled"
    And the client should receive a cancellation notification
    And the time slot should become available again

  @provider @appointments
  Scenario: Add appointment outcome notes
    Given I have a completed appointment with "John Doe"
    When I click on the appointment
    And I click "Add Outcome Notes"
    And I enter notes "Patient responding well to treatment. Continue current protocol."
    And I mark appointment as "Completed"
    And I click save
    Then the outcome notes should be saved
    And the appointment status should be "Completed"
    And the notes should be visible in client's record

  @provider @appointments
  Scenario: Verify calendar integration
    Given I have booked an appointment
    When I check my calendar
    Then the appointment should appear in my calendar
    And it should sync with external calendars (Google, Outlook)
    And updates should be reflected across all platforms

  @provider @appointments
  Scenario: Book recurring appointments
    Given I have a client "John Doe"
    When I click "Book Appointment"
    And I select client "John Doe"
    And I select event type "Follow-up"
    And I enable "Recurring Appointment"
    And I set recurrence to "Weekly"
    And I set number of occurrences to "4"
    And I select start date "2024-02-15"
    And I select time "10:00 AM"
    And I click confirm booking
    Then 4 appointments should be created
    And they should be scheduled weekly starting from "2024-02-15"

  @provider @appointments @validation
  Scenario: Prevent double booking
    Given I have an appointment at "2024-02-15" at "10:00 AM"
    When I try to book another appointment at the same time
    Then I should see an error message "Time slot already booked"
    And the booking should be prevented

  @provider @appointments
  Scenario: Mark appointment as no-show
    Given I have an appointment with "John Doe" that was scheduled for today
    And the client did not attend
    When I click on the appointment
    And I click "Mark as No-Show"
    And I confirm the action
    Then the appointment status should be "No-Show"
    And it should be recorded in the client's history
    And I should be able to add notes about the no-show
