Feature: Provider Availability Management
  As a provider
  I want to manage my availability schedule
  So that clients can book appointments during my available times

  Background:
    Given I am logged in as a provider
    And I navigate to availability settings

  @smoke @provider @availability
  Scenario: Create new availability schedule
    When I click "Create New Schedule"
    And I enter schedule name "Regular Office Hours"
    And I click save
    Then I should see a success message "Schedule created"
    And the schedule should appear in my schedules list

  @provider @availability
  Scenario: Set weekly recurring hours
    Given I have created a schedule "Regular Office Hours"
    When I select Monday
    And I add time slot from "09:00 AM" to "12:00 PM"
    And I add time slot from "01:00 PM" to "05:00 PM"
    And I select Tuesday through Friday
    And I copy Monday's schedule
    And I click save
    Then I should see a success message
    And my availability should be set for weekdays

  @provider @availability
  Scenario: Add multiple time slots per day
    Given I have created a schedule
    When I select Wednesday
    And I add time slot from "09:00 AM" to "11:00 AM"
    And I click "Add Another Slot"
    And I add time slot from "02:00 PM" to "04:00 PM"
    And I click "Add Another Slot"
    And I add time slot from "06:00 PM" to "08:00 PM"
    And I click save
    Then Wednesday should have 3 time slots
    And all slots should be available for booking

  @provider @availability
  Scenario: Set timezone for availability
    Given I have created a schedule
    When I click timezone settings
    And I select timezone "America/New_York"
    And I click save
    Then my availability should be displayed in "America/New_York" timezone
    And clients should see times converted to their timezone

  @provider @availability
  Scenario: Create schedule override for specific date
    Given I have a default schedule set
    When I navigate to calendar view
    And I select date "2024-02-14"
    And I click "Create Override"
    And I set custom hours from "10:00 AM" to "02:00 PM"
    And I click save
    Then the override should be applied for "2024-02-14"
    And my default schedule should apply for other dates

  @provider @availability
  Scenario: Block out specific date
    Given I have a default schedule set
    When I navigate to calendar view
    And I select date "2024-03-15"
    And I click "Block Date"
    And I enter reason "Conference attendance"
    And I click save
    Then "2024-03-15" should be marked as unavailable
    And clients should not be able to book on this date

  @provider @availability
  Scenario: Edit existing availability
    Given I have a schedule "Regular Office Hours"
    When I click edit on the schedule
    And I change Monday hours from "09:00 AM - 05:00 PM" to "10:00 AM - 06:00 PM"
    And I click save
    Then I should see a success message
    And the updated hours should be reflected
    And future appointments should respect new hours

  @provider @availability
  Scenario: Delete availability schedule
    Given I have multiple schedules
    And I have a schedule "Old Schedule" that is not in use
    When I click delete on "Old Schedule"
    And I confirm the deletion
    Then I should see a success message "Schedule deleted"
    And the schedule should be removed from the list

  @provider @availability
  Scenario: Set default schedule
    Given I have multiple schedules:
      | Schedule Name        |
      | Regular Office Hours |
      | Extended Hours       |
      | Weekend Hours        |
    When I click "Set as Default" on "Regular Office Hours"
    Then "Regular Office Hours" should be marked as default
    And it should be automatically applied to new weeks

  @provider @availability
  Scenario: Set buffer time between appointments
    Given I have created a schedule
    When I navigate to booking settings
    And I set buffer time before appointments to "15 minutes"
    And I set buffer time after appointments to "10 minutes"
    And I click save
    Then appointments should be spaced with the specified buffer times
    And clients should not be able to book back-to-back slots

  @provider @availability @validation
  Scenario: Prevent overlapping time slots
    Given I have created a schedule
    When I select Monday
    And I add time slot from "09:00 AM" to "12:00 PM"
    And I try to add time slot from "11:00 AM" to "02:00 PM"
    Then I should see an error message "Time slots cannot overlap"
    And the overlapping slot should not be saved
