package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LabFileUploadPage - Page Factory implementation for Provider Lab File Upload page
 * Based on: apps/web/src/features/lab-result
 * 
 * Handles lab file upload and processing workflows including:
 * - Lab file upload for existing clients
 * - PDF and image lab report uploads
 * - AI processing verification
 * - Extracted biomarker review
 * - Biomarker value editing
 * - Unmatched biomarker matching
 * - Lab collection date
 * - Lab report completion status
 * - Lab report view in client profile
 */
public class LabFileUploadPage extends BasePage {

    // Lab File Upload Form Elements
    @FindBy(xpath = "//select[@name='clientId' or contains(text(),'Select Client')]")
    private WebElement clientSelectDropdown;

    @FindBy(xpath = "//input[@placeholder='Search for client']")
    private WebElement clientSearchInput;

    @FindBy(xpath = "//div[contains(@class,'client-list')]")
    private WebElement clientListContainer;

    // File Upload
    @FindBy(xpath = "//input[@type='file' and contains(@accept,'pdf,image')]")
    private WebElement fileUploadInput;

    @FindBy(xpath = "//button[contains(text(),'Choose File')] | //button[contains(text(),'Select File')]")
    private WebElement chooseFileButton;

    @FindBy(xpath = "//div[contains(@class,'file-upload')] | //div[contains(@class,'drop-zone')]")
    private WebElement fileUploadDropZone;

    // Lab Details
    @FindBy(xpath = "//input[@name='labName' or @placeholder='Lab name']")
    private WebElement labNameInput;

    @FindBy(xpath = "//input[@name='collectionDate' or @placeholder='Select collection date']")
    private WebElement collectionDateInput;

    @FindBy(xpath = "//textarea[@name='notes' or @placeholder='Add lab notes']")
    private WebElement notesInput;

    // Action Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Upload Lab File')]")
    private WebElement uploadLabFileButton;

    @FindBy(xpath = "//button[contains(text(),'Upload Another')]")
    private WebElement uploadAnotherButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // AI Processing Indicators
    @FindBy(xpath = "//div[contains(text(),'Processing')]")
    private WebElement processingIndicator;

    @FindBy(xpath = "//div[contains(text(),'Processing complete')]")
    private WebElement processingCompleteMessage;

    @FindBy(xpath = "//span[contains(text(),'AI processing initiated')]")
    private WebElement aiProcessingInitiatedMessage;

    // Biomarker Results
    @FindBy(xpath = "//table[contains(@class,'biomarker')] | //div[contains(@class,'biomarker-results')]")
    private WebElement biomarkerResultsContainer;

    @FindBy(xpath = "//tr[contains(@class,'biomarker-row')] | //div[contains(@class,'biomarker-item')]")
    private WebElement biomarkerRow;

    @FindBy(xpath = "//button[contains(text(),'Review Biomarkers')]")
    private WebElement reviewBiomarkersButton;

    @FindBy(xpath = "//button[contains(text(),'Edit Biomarker')]")
    private WebElement editBiomarkerButton;

    @FindBy(xpath = "//input[@placeholder='Enter biomarker value']")
    private WebElement biomarkerValueInput;

    @FindBy(xpath = "//button[contains(text(),'Save Biomarker')]")
    private WebElement saveBiomarkerButton;

    // Unmatched Biomarkers
    @FindBy(xpath = "//div[contains(text(),'Unmatched Biomarkers')]")
    private WebElement unmatchedBiomarkersSection;

    @FindBy(xpath = "//button[contains(text(),'Match Biomarker')]")
    private WebElement matchBiomarkerButton;

    @FindBy(xpath = "//select[contains(@name,'matchedBiomarker')]")
    private WebElement biomarkerMatchSelect;

    // Lab Report Actions
    @FindBy(xpath = "//button[contains(text(),'Mark as Completed')]")
    private WebElement markAsCompletedButton;

    @FindBy(xpath = "//button[contains(text(),'View Lab Report')]")
    private WebElement viewLabReportButton;

    @FindBy(xpath = "//button[contains(text(),'Download Report')]")
    private WebElement downloadReportButton;

    // Lab Report List
    @FindBy(xpath = "//table[contains(@class,'lab-reports')] | //div[contains(@class,'lab-report-list')]")
    private WebElement labReportsTable;

    @FindBy(xpath = "//tr[contains(@class,'lab-report-row')] | //div[contains(@class,'lab-report-item')]")
    private WebElement labReportRow;

    @FindBy(xpath = "//span[contains(text(),'Completed')]")
    private WebElement completedStatus;

    @FindBy(xpath = "//span[contains(text(),'Processing')]")
    private WebElement processingStatus;

    @FindBy(xpath = "//span[contains(text(),'Pending Review')]")
    private WebElement pendingReviewStatus;

    // Search and Filter
    @FindBy(xpath = "//input[@placeholder='Search lab reports']")
    private WebElement searchInput;

    @FindBy(xpath = "//input[@type='date' and @name='dateFrom']")
    private WebElement dateFromFilter;

    @FindBy(xpath = "//input[@type='date' and @name='dateTo']")
    private WebElement dateToFilter;

    @FindBy(xpath = "//select[contains(@name,'status')]")
    private WebElement statusFilterSelect;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Lab file uploaded successfully')]")
    private WebElement labFileUploadedMessage;

    @FindBy(xpath = "//div[contains(text(),'Biomarkers extracted successfully')]")
    private WebElement biomarkersExtractedMessage;

    @FindBy(xpath = "//div[contains(text(),'Biomarker updated successfully')]")
    private WebElement biomarkerUpdatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Invalid file type')]")
    private WebElement invalidFileTypeError;

    @FindBy(xpath = "//div[contains(text(),'File size exceeds maximum')]")
    private WebElement fileSizeExceededError;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public LabFileUploadPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("LabFileUploadPage initialized");
    }

    /**
     * Check if lab file upload page is displayed
     *
     * @return boolean
     */
    public boolean isLabFileUploadPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(uploadLabFileButton);
        } catch (Exception e) {
            logger.debug("Lab file upload page not displayed");
            return false;
        }
    }

    /**
     * Search and select client by name
     *
     * @param clientName Client name
     */
    public void selectClientByName(String clientName) {
        LogHelper.logStep("Selecting client: " + clientName);
        waitHelper.waitForElementVisible(clientSearchInput);
        enterText(clientSearchInput, clientName);
        // Wait for dropdown and select
        waitHelper.waitForElementVisible(clientListContainer);
    }

    /**
     * Upload lab file
     *
     * @param filePath File path
     */
    public void uploadLabFile(String filePath) {
        LogHelper.logStep("Uploading lab file from: " + filePath);
        waitHelper.waitForElementVisible(fileUploadInput);
        fileUploadInput.sendKeys(filePath);
    }

    /**
     * Enter lab name
     *
     * @param labName Lab name
     */
    public void enterLabName(String labName) {
        LogHelper.logStep("Entering lab name: " + labName);
        waitHelper.waitForElementVisible(labNameInput);
        enterText(labNameInput, labName);
    }

    /**
     * Set lab collection date
     *
     * @param date Lab collection date
     */
    public void setCollectionDate(String date) {
        LogHelper.logStep("Setting lab collection date: " + date);
        waitHelper.waitForElementVisible(collectionDateInput);
        enterText(collectionDateInput, date);
    }

    /**
     * Add lab notes
     *
     * @param notes Lab notes
     */
    public void addLabNotes(String notes) {
        LogHelper.logStep("Adding lab notes");
        waitHelper.waitForElementVisible(notesInput);
        enterText(notesInput, notes);
    }

    /**
     * Click upload lab file button
     */
    public void clickUploadLabFileButton() {
        LogHelper.logStep("Clicking Upload Lab File button");
        waitHelper.waitForElementClickable(uploadLabFileButton);
        click(uploadLabFileButton);
    }

    /**
     * Click upload another button
     */
    public void clickUploadAnotherButton() {
        LogHelper.logStep("Clicking Upload Another button");
        waitHelper.waitForElementClickable(uploadAnotherButton);
        click(uploadAnotherButton);
    }

    /**
     * Upload lab file for existing client
     *
     * @param clientName Client name
     * @param filePath File path
     */
    public void uploadLabFileForClient(String clientName, String filePath) {
        LogHelper.logStep("Uploading lab file for client: " + clientName);
        selectClientByName(clientName);
        uploadLabFile(filePath);
        clickUploadLabFileButton();
    }

    /**
     * Upload lab file with complete details
     *
     * @param clientName Client name
     * @param filePath File path
     * @param labName Lab name
     * @param collectionDate Collection date
     */
    public void uploadLabFileWithDetails(String clientName, String filePath, String labName, String collectionDate) {
        LogHelper.logStep("Uploading lab file with complete details");
        selectClientByName(clientName);
        uploadLabFile(filePath);
        enterLabName(labName);
        setCollectionDate(collectionDate);
        clickUploadLabFileButton();
    }

    /**
     * Check if lab file uploaded message is displayed
     *
     * @return boolean
     */
    public boolean isLabFileUploadedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(labFileUploadedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if AI processing initiated message is displayed
     *
     * @return boolean
     */
    public boolean isAiProcessingInitiatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(aiProcessingInitiatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for processing to complete
     */
    public void waitForProcessingComplete() {
        LogHelper.logStep("Waiting for AI processing to complete");
        waitHelper.waitForElementVisible(processingCompleteMessage);
    }

    /**
     * Check if biomarkers extracted message is displayed
     *
     * @return boolean
     */
    public boolean isBiomarkersExtractedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(biomarkersExtractedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click review biomarkers button
     */
    public void clickReviewBiomarkersButton() {
        LogHelper.logStep("Clicking Review Biomarkers button");
        waitHelper.waitForElementClickable(reviewBiomarkersButton);
        click(reviewBiomarkersButton);
    }

    /**
     * Check if biomarker results are displayed
     *
     * @return boolean
     */
    public boolean areBiomarkerResultsDisplayed() {
        try {
            return waitHelper.isElementDisplayed(biomarkerResultsContainer);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Edit biomarker value
     *
     * @param newValue New biomarker value
     */
    public void editBiomarkerValue(String newValue) {
        LogHelper.logStep("Editing biomarker value to: " + newValue);
        waitHelper.waitForElementClickable(editBiomarkerButton);
        click(editBiomarkerButton);
        waitHelper.waitForElementVisible(biomarkerValueInput);
        enterText(biomarkerValueInput, newValue);
        waitHelper.waitForElementClickable(saveBiomarkerButton);
        click(saveBiomarkerButton);
    }

    /**
     * Check if biomarker updated message is displayed
     *
     * @return boolean
     */
    public boolean isBiomarkerUpdatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(biomarkerUpdatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if unmatched biomarkers section is displayed
     *
     * @return boolean
     */
    public boolean isUnmatchedBiomarkersSectionDisplayed() {
        try {
            return waitHelper.isElementDisplayed(unmatchedBiomarkersSection);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Match unmatched biomarker
     */
    public void matchUnmatchedBiomarker() {
        LogHelper.logStep("Matching unmatched biomarker");
        waitHelper.waitForElementClickable(matchBiomarkerButton);
        click(matchBiomarkerButton);
        waitHelper.waitForElementClickable(biomarkerMatchSelect);
        click(biomarkerMatchSelect);
    }

    /**
     * Mark lab report as completed
     */
    public void markLabReportAsCompleted() {
        LogHelper.logStep("Marking lab report as completed");
        waitHelper.waitForElementClickable(markAsCompletedButton);
        click(markAsCompletedButton);
    }

    /**
     * View lab report
     */
    public void viewLabReport() {
        LogHelper.logStep("Viewing lab report");
        waitHelper.waitForElementClickable(viewLabReportButton);
        click(viewLabReportButton);
    }

    /**
     * Download lab report
     */
    public void downloadLabReport() {
        LogHelper.logStep("Downloading lab report");
        waitHelper.waitForElementClickable(downloadReportButton);
        click(downloadReportButton);
    }

    /**
     * Check if invalid file type error is displayed
     *
     * @return boolean
     */
    public boolean isInvalidFileTypeErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invalidFileTypeError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if file size exceeded error is displayed
     *
     * @return boolean
     */
    public boolean isFileSizeExceededErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(fileSizeExceededError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * View lab reports list
     *
     * @return boolean - true if lab reports table is displayed
     */
    public boolean viewLabReportsList() {
        LogHelper.logStep("Viewing lab reports list");
        try {
            return waitHelper.isElementDisplayed(labReportsTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search lab reports
     *
     * @param searchTerm Search term
     */
    public void searchLabReports(String searchTerm) {
        LogHelper.logStep("Searching lab reports: " + searchTerm);
        waitHelper.waitForElementVisible(searchInput);
        enterText(searchInput, searchTerm);
    }

    /**
     * Filter lab reports by status
     *
     * @param status Status to filter
     */
    public void filterLabReportsByStatus(String status) {
        LogHelper.logStep("Filtering lab reports by status: " + status);
        waitHelper.waitForElementClickable(statusFilterSelect);
        click(statusFilterSelect);
    }

    /**
     * Click cancel button
     */
    public void clickCancelButton() {
        LogHelper.logStep("Clicking Cancel button");
        waitHelper.waitForElementClickable(cancelButton);
        click(cancelButton);
    }
}
