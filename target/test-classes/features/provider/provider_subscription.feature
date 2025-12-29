Feature: Provider Subscription Management (Chargebee)
  As a provider
  I want to manage my subscription plan
  So that I can access the features I need

  Background:
    Given I am logged in as a provider
    And I navigate to subscription settings

  @smoke @provider @subscription
  Scenario: Select free plan
    Given I am a new provider without a subscription
    When I select the "Free" plan
    And I click confirm plan selection
    Then I should see a success message
    And my plan should be set to "Free"
    And I should see free plan features listed

  @provider @subscription
  Scenario: Verify free plan features
    Given I am on the "Free" plan
    When I view my plan details
    Then I should see the following features:
      | Feature              | Limit |
      | Active Clients       |     5 |
      | Document Storage     |  1 GB |
      | Monthly Appointments |    20 |
      | Email Support        | Yes   |
      | Advanced Analytics   | No    |

  @provider @subscription @payment
  Scenario: Upgrade to Navigator plan
    Given I am on the "Free" plan
    When I click upgrade plan
    And I select the "Navigator" plan
    And I review the plan details
    And I click proceed to payment
    Then I should be redirected to Chargebee payment page
    And I should see the plan price displayed

  @provider @subscription @payment
  Scenario: Enter payment details
    Given I am on the Chargebee payment page for "Navigator" plan
    When I enter card number "4111111111111111"
    And I enter expiry date "12/25"
    And I enter CVV "123"
    And I enter billing name "John Doe"
    And I enter billing address details
    And I click complete purchase
    Then I should see payment processing
    And I should receive a payment confirmation

  @provider @subscription
  Scenario: Verify subscription activation
    Given I have successfully paid for the "Navigator" plan
    When I return to the MTOmics platform
    Then my subscription should be active
    And I should see "Navigator" as my current plan
    And I should have access to Navigator plan features
    And I should receive a subscription confirmation email

  @provider @subscription
  Scenario: View billing history
    Given I have an active subscription
    When I navigate to billing history
    Then I should see a list of all transactions
    And each transaction should show:
      | Field        |
      | Date         |
      | Plan         |
      | Amount       |
      | Status       |
      | Invoice Link |

  @provider @subscription
  Scenario: Cancel subscription
    Given I have an active "Navigator" subscription
    When I click cancel subscription
    And I confirm the cancellation
    And I provide cancellation reason "Switching to another platform"
    Then I should see a cancellation confirmation
    And my subscription should be marked for cancellation at period end
    And I should receive a cancellation confirmation email

  @provider @subscription
  Scenario: Downgrade to free plan
    Given I have an active "Navigator" subscription
    And my subscription is set to cancel at period end
    When the billing period ends
    Then my plan should automatically downgrade to "Free"
    And I should see free plan limitations applied
    And I should receive a downgrade notification email
