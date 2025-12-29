Feature: Provider Document Upload
  As a provider
  I want to upload documents for my clients
  So that I can maintain their medical records digitally

  Background:
    Given I am logged in as a provider
    And I have an existing client "John Doe"
    And I navigate to the client's profile

  @smoke @provider @document-upload
  Scenario: Upload document for existing client
    When I click the "Upload Document" button
    And I select document type "Lab Reports"
    And I choose file "lab_report.pdf"
    And I click upload
    Then I should see a success message "Document uploaded successfully"
    And the document should appear in the client's document list

  @provider @document-upload
  Scenario Outline: Select document type
    When I click the "Upload Document" button
    And I select document type "<document_type>"
    And I choose a file
    And I click upload
    Then the document should be categorized as "<document_type>"

    Examples:
      | document_type   |
      | Lab Reports     |
      | Imaging         |
      | Genetic         |
      | Medical Records |
      | Prescriptions   |
      | Insurance       |
      | Other           |

  @provider @document-upload
  Scenario: Upload PDF document
    When I click the "Upload Document" button
    And I select document type "Medical Records"
    And I choose PDF file "medical_history.pdf"
    And I click upload
    Then I should see a success message
    And the PDF should be viewable in the document viewer

  @provider @document-upload
  Scenario: Upload image document
    When I click the "Upload Document" button
    And I select document type "Lab Reports"
    And I choose image file "lab_result.jpg"
    And I click upload
    Then I should see a success message
    And the image should be viewable in the document viewer

  @provider @document-upload
  Scenario: Set document collection date
    When I click the "Upload Document" button
    And I select document type "Lab Reports"
    And I choose a file
    And I set collection date to "2024-01-15"
    And I click upload
    Then the document should be saved with collection date "2024-01-15"
    And the document should appear in the timeline at the correct date

  @provider @document-upload
  Scenario: Add document notes
    When I click the "Upload Document" button
    And I select document type "Lab Reports"
    And I choose a file
    And I enter notes "Follow-up required for elevated cholesterol"
    And I click upload
    Then the document should be saved with the notes
    And the notes should be visible when viewing the document

  @provider @document-upload
  Scenario: Upload multiple documents
    When I click the "Upload Document" button
    And I select document type "Lab Reports"
    And I choose multiple files:
      | Filename         |
      | lab_report_1.pdf |
      | lab_report_2.pdf |
      | lab_result.jpg   |
    And I click upload
    Then I should see a success message "3 documents uploaded"
    And all documents should appear in the client's document list

  @provider @document-upload
  Scenario: Verify document appears in client record
    Given I have uploaded a document "lab_report.pdf"
    When I navigate to the client's documents section
    Then I should see "lab_report.pdf" in the list
    And the document should show:
      | Field           |
      | Document Name   |
      | Document Type   |
      | Upload Date     |
      | Collection Date |
      | Uploaded By     |
      | File Size       |

  @provider @document-upload
  Scenario: Delete uploaded document
    Given I have uploaded a document "old_report.pdf"
    When I navigate to the client's documents section
    And I click delete on "old_report.pdf"
    And I confirm the deletion
    Then I should see a success message "Document deleted"
    And the document should be removed from the list

  @provider @document-upload
  Scenario: Download uploaded document
    Given I have uploaded a document "lab_report.pdf"
    When I navigate to the client's documents section
    And I click download on "lab_report.pdf"
    Then the document should be downloaded to my computer
    And the filename should be "lab_report.pdf"

  @provider @document-upload @validation
  Scenario: Upload with invalid file type
    When I click the "Upload Document" button
    And I try to upload file "document.exe"
    Then I should see an error message "Invalid file type"
    And the upload should be prevented

  @provider @document-upload @validation
  Scenario: Upload file exceeding size limit
    When I click the "Upload Document" button
    And I try to upload a file larger than 50MB
    Then I should see an error message "File size exceeds limit"
    And the upload should be prevented
