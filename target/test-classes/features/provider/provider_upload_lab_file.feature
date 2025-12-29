Feature: Provider Lab File Upload
  As a provider
  I want to upload lab files for my clients
  So that AI can extract biomarkers and track health trends

  Background:
    Given I am logged in as a provider
    And I have an existing client "John Doe"
    And I navigate to the client's lab results section

  @smoke @provider @lab-upload
  Scenario: Upload lab file for existing client
    When I click the "Upload Lab File" button
    And I choose file "lab_results.pdf"
    And I set lab collection date to "2024-01-15"
    And I click upload
    Then I should see a success message "Lab file uploaded successfully"
    And I should see "Processing..." status

  @provider @lab-upload
  Scenario: Upload PDF lab report
    When I click the "Upload Lab File" button
    And I choose PDF file "comprehensive_panel.pdf"
    And I set lab collection date to "2024-01-15"
    And I click upload
    Then I should see a success message
    And the PDF should be sent for AI processing
    And I should see processing status indicator

  @provider @lab-upload
  Scenario: Upload image lab report
    When I click the "Upload Lab File" button
    And I choose image file "lab_report.jpg"
    And I set lab collection date to "2024-01-15"
    And I click upload
    Then I should see a success message
    And the image should be sent for AI processing
    And I should see processing status indicator

  @provider @lab-upload @ai-processing
  Scenario: Verify AI processing initiated
    Given I have uploaded a lab file "lab_results.pdf"
    When the file is uploaded successfully
    Then AI processing should be initiated automatically
    And I should see "Processing" status
    And I should receive a notification when processing completes

  @provider @lab-upload @ai-processing
  Scenario: Review extracted biomarkers
    Given AI has processed the lab file "lab_results.pdf"
    When I view the lab report
    Then I should see a list of extracted biomarkers
    And each biomarker should display:
      | Field            |
      | Biomarker Name   |
      | Value            |
      | Unit             |
      | Reference Range  |
      | Status           |
      | Confidence Score |

  @provider @lab-upload
  Scenario: Edit biomarker values
    Given AI has extracted biomarkers from a lab file
    When I click edit on a biomarker
    And I update the value from "150" to "155"
    And I update the unit from "mg/dL" to "mmol/L"
    And I click save
    Then I should see a success message
    And the biomarker should be updated with new values
    And the change should be logged in audit trail

  @provider @lab-upload @ai-processing
  Scenario: Match unmatched biomarkers
    Given AI has extracted biomarkers with some unmatched items
    When I view the unmatched biomarkers section
    And I see biomarker "Cholesterol Total"
    And I click "Match to Standard"
    And I select "Total Cholesterol" from the standard list
    And I click confirm match
    Then the biomarker should be matched
    And it should appear in the matched biomarkers list

  @provider @lab-upload
  Scenario: Set lab collection date
    When I click the "Upload Lab File" button
    And I choose a file
    And I click the collection date field
    And I select date "2024-01-15"
    And I click upload
    Then the lab report should be saved with collection date "2024-01-15"
    And it should appear in the timeline at the correct date

  @provider @lab-upload
  Scenario: Mark lab report as completed
    Given I have uploaded and reviewed a lab file
    And all biomarkers are verified
    When I click "Mark as Complete"
    Then the lab report status should change to "Completed"
    And the client should be notified
    And the report should be locked from further edits

  @provider @lab-upload
  Scenario: View lab report in client profile
    Given I have completed processing a lab file
    When I navigate to the client's profile
    And I go to the lab results section
    Then I should see the lab report listed
    And I should be able to:
      | Action                   |
      | View biomarker details   |
      | View trends over time    |
      | Download original file   |
      | Export biomarkers to PDF |
      | Share with client        |

  @provider @lab-upload
  Scenario: Add notes to lab report
    Given I have uploaded a lab file
    When I click "Add Notes"
    And I enter "Patient should follow up for elevated glucose levels"
    And I click save
    Then the notes should be saved with the lab report
    And the notes should be visible to the client

  @provider @lab-upload @validation
  Scenario: Upload without collection date
    When I click the "Upload Lab File" button
    And I choose a file
    And I do not set a collection date
    And I click upload
    Then I should see an error message "Collection date is required"
    And the upload should be prevented

  @provider @lab-upload
  Scenario: Delete lab report
    Given I have uploaded a lab file "incorrect_lab.pdf"
    When I click delete on the lab report
    And I confirm the deletion
    Then I should see a success message "Lab report deleted"
    And all associated biomarkers should be removed
    And the report should be removed from the client's timeline
