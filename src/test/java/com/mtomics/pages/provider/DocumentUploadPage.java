package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * DocumentUploadPage - Page Factory implementation for Provider Document Upload page
 * Based on: apps/web/src/features/documents
 * 
 * Handles document upload workflows including:
 * - Document upload for existing clients
 * - Document type selection (lab reports, imaging, genetic, medical records)
 * - PDF and image uploads
 * - Document collection date
 * - Document notes
 * - Multiple document uploads
 * - Document deletion and download
 */
public class DocumentUploadPage extends BasePage {

    // Document Upload Form Elements
    @FindBy(xpath = "//select[@name='clientId' or contains(text(),'Select Client')]")
    private WebElement clientSelectDropdown;

    @FindBy(xpath = "//input[@placeholder='Search for client']")
    private WebElement clientSearchInput;

    @FindBy(xpath = "//div[contains(@class,'client-list')]")
    private WebElement clientListContainer;

    // Document Type Selection
    @FindBy(xpath = "//select[@name='documentType' or contains(text(),'Document Type')]")
    private WebElement documentTypeSelect;

    @FindBy(xpath = "//label[contains(text(),'Lab Reports')]")
    private WebElement labReportsOption;

    @FindBy(xpath = "//label[contains(text(),'Imaging')]")
    private WebElement imagingOption;

    @FindBy(xpath = "//label[contains(text(),'Genetic')]")
    private WebElement geneticOption;

    @FindBy(xpath = "//label[contains(text(),'Medical Records')]")
    private WebElement medicalRecordsOption;

    @FindBy(xpath = "//label[contains(text(),'Other')]")
    private WebElement otherOption;

    // File Upload
    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileUploadInput;

    @FindBy(xpath = "//button[contains(text(),'Choose File')] | //button[contains(text(),'Select File')]")
    private WebElement chooseFileButton;

    @FindBy(xpath = "//div[contains(@class,'file-upload')] | //div[contains(@class,'drop-zone')]")
    private WebElement fileUploadDropZone;

    // Document Details
    @FindBy(xpath = "//input[@name='collectionDate' or @placeholder='Select collection date']")
    private WebElement collectionDateInput;

    @FindBy(xpath = "//textarea[@name='notes' or @placeholder='Add document notes']")
    private WebElement notesInput;

    @FindBy(xpath = "//input[@name='documentTitle' or @placeholder='Document title']")
    private WebElement documentTitleInput;

    // Action Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Upload Document')]")
    private WebElement uploadDocumentButton;

    @FindBy(xpath = "//button[contains(text(),'Upload Another')]")
    private WebElement uploadAnotherButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Document List
    @FindBy(xpath = "//table[contains(@class,'documents')]")
    private WebElement documentsTable;

    @FindBy(xpath = "//tr[contains(@class,'document-row')]")
    private WebElement documentRow;

    @FindBy(xpath = "//td[contains(text(),'Lab Reports')]")
    private WebElement labReportCell;

    // Document Actions
    @FindBy(xpath = "//button[contains(text(),'Download')]")
    private WebElement downloadButton;

    @FindBy(xpath = "//button[contains(text(),'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[contains(text(),'View')]")
    private WebElement viewButton;

    @FindBy(xpath = "//button[contains(text(),'Share')]")
    private WebElement shareButton;

    // Search and Filter
    @FindBy(xpath = "//input[@placeholder='Search documents']")
    private WebElement searchInput;

    @FindBy(xpath = "//select[contains(@name,'documentType')]")
    private WebElement filterByTypeSelect;

    @FindBy(xpath = "//input[@type='date' and @name='dateFrom']")
    private WebElement dateFromFilter;

    @FindBy(xpath = "//input[@type='date' and @name='dateTo']")
    private WebElement dateToFilter;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Document uploaded successfully')]")
    private WebElement documentUploadedMessage;

    @FindBy(xpath = "//div[contains(text(),'Document deleted successfully')]")
    private WebElement documentDeletedMessage;

    @FindBy(xpath = "//div[contains(text(),'Invalid file type')]")
    private WebElement invalidFileTypeError;

    @FindBy(xpath = "//div[contains(text(),'File size exceeds maximum')]")
    private WebElement fileSizeExceededError;

    @FindBy(xpath = "//div[contains(text(),'Document appears in client record')]")
    private WebElement documentInClientRecordMessage;

    @FindBy(xpath = "//span[contains(text(),'No documents found')]")
    private WebElement noDocumentsMessage;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public DocumentUploadPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("DocumentUploadPage initialized");
    }

    /**
     * Check if document upload page is displayed
     *
     * @return boolean
     */
    public boolean isDocumentUploadPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(uploadDocumentButton);
        } catch (Exception e) {
            logger.debug("Document upload page not displayed");
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
     * Click client select dropdown
     */
    public void clickClientSelectDropdown() {
        LogHelper.logStep("Clicking client select dropdown");
        waitHelper.waitForElementClickable(clientSelectDropdown);
        click(clientSelectDropdown);
    }

    /**
     * Select document type
     *
     * @param documentType Document type (Lab Reports, Imaging, Genetic, Medical Records, Other)
     */
    public void selectDocumentType(String documentType) {
        LogHelper.logStep("Selecting document type: " + documentType);
        waitHelper.waitForElementClickable(documentTypeSelect);
        click(documentTypeSelect);
        
        switch (documentType.toLowerCase()) {
            case "lab reports":
                waitHelper.waitForElementClickable(labReportsOption);
                click(labReportsOption);
                break;
            case "imaging":
                waitHelper.waitForElementClickable(imagingOption);
                click(imagingOption);
                break;
            case "genetic":
                waitHelper.waitForElementClickable(geneticOption);
                click(geneticOption);
                break;
            case "medical records":
                waitHelper.waitForElementClickable(medicalRecordsOption);
                click(medicalRecordsOption);
                break;
            case "other":
                waitHelper.waitForElementClickable(otherOption);
                click(otherOption);
                break;
            default:
                logger.warn("Unknown document type: " + documentType);
        }
    }

    /**
     * Upload file
     *
     * @param filePath File path
     */
    public void uploadFile(String filePath) {
        LogHelper.logStep("Uploading file from: " + filePath);
        waitHelper.waitForElementVisible(fileUploadInput);
        fileUploadInput.sendKeys(filePath);
    }

    /**
     * Enter document title
     *
     * @param title Document title
     */
    public void enterDocumentTitle(String title) {
        LogHelper.logStep("Entering document title: " + title);
        waitHelper.waitForElementVisible(documentTitleInput);
        enterText(documentTitleInput, title);
    }

    /**
     * Set collection date
     *
     * @param date Collection date
     */
    public void setCollectionDate(String date) {
        LogHelper.logStep("Setting collection date: " + date);
        waitHelper.waitForElementVisible(collectionDateInput);
        enterText(collectionDateInput, date);
    }

    /**
     * Add document notes
     *
     * @param notes Document notes
     */
    public void addDocumentNotes(String notes) {
        LogHelper.logStep("Adding document notes");
        waitHelper.waitForElementVisible(notesInput);
        enterText(notesInput, notes);
    }

    /**
     * Click upload document button
     */
    public void clickUploadDocumentButton() {
        LogHelper.logStep("Clicking Upload Document button");
        waitHelper.waitForElementClickable(uploadDocumentButton);
        click(uploadDocumentButton);
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
     * Upload document for existing client
     *
     * @param clientName Client name
     * @param documentType Document type
     * @param filePath File path
     */
    public void uploadDocumentForClient(String clientName, String documentType, String filePath) {
        LogHelper.logStep("Uploading document for client: " + clientName);
        selectClientByName(clientName);
        selectDocumentType(documentType);
        uploadFile(filePath);
        clickUploadDocumentButton();
    }

    /**
     * Upload document with complete details
     *
     * @param clientName Client name
     * @param documentType Document type
     * @param filePath File path
     * @param collectionDate Collection date
     * @param notes Document notes
     */
    public void uploadDocumentWithDetails(String clientName, String documentType, String filePath, 
                                         String collectionDate, String notes) {
        LogHelper.logStep("Uploading document with complete details");
        selectClientByName(clientName);
        selectDocumentType(documentType);
        uploadFile(filePath);
        setCollectionDate(collectionDate);
        addDocumentNotes(notes);
        clickUploadDocumentButton();
    }

    /**
     * Upload multiple documents
     *
     * @param clientName Client name
     * @param documents Array of document objects (documentType, filePath)
     */
    public void uploadMultipleDocuments(String clientName, String[][] documents) {
        LogHelper.logStep("Uploading multiple documents");
        selectClientByName(clientName);
        
        for (int i = 0; i < documents.length; i++) {
            String documentType = documents[i][0];
            String filePath = documents[i][1];
            
            selectDocumentType(documentType);
            uploadFile(filePath);
            
            if (i < documents.length - 1) {
                clickUploadAnotherButton();
            } else {
                clickUploadDocumentButton();
            }
        }
    }

    /**
     * Check if document uploaded message is displayed
     *
     * @return boolean
     */
    public boolean isDocumentUploadedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(documentUploadedMessage);
        } catch (Exception e) {
            return false;
        }
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
     * View documents list for client
     *
     * @return boolean - true if documents table is displayed
     */
    public boolean viewDocumentsListForClient() {
        LogHelper.logStep("Viewing documents list");
        try {
            return waitHelper.isElementDisplayed(documentsTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Download document
     */
    public void downloadDocument() {
        LogHelper.logStep("Downloading document");
        waitHelper.waitForElementClickable(downloadButton);
        click(downloadButton);
    }

    /**
     * Delete document
     */
    public void deleteDocument() {
        LogHelper.logStep("Deleting document");
        waitHelper.waitForElementClickable(deleteButton);
        click(deleteButton);
    }

    /**
     * Check if document deleted message is displayed
     *
     * @return boolean
     */
    public boolean isDocumentDeletedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(documentDeletedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search documents
     *
     * @param searchTerm Search term
     */
    public void searchDocuments(String searchTerm) {
        LogHelper.logStep("Searching documents: " + searchTerm);
        waitHelper.waitForElementVisible(searchInput);
        enterText(searchInput, searchTerm);
    }

    /**
     * Filter documents by type
     *
     * @param documentType Document type to filter
     */
    public void filterDocumentsByType(String documentType) {
        LogHelper.logStep("Filtering documents by type: " + documentType);
        waitHelper.waitForElementClickable(filterByTypeSelect);
        click(filterByTypeSelect);
    }

    /**
     * Click cancel button
     */
    public void clickCancelButton() {
        LogHelper.logStep("Clicking Cancel button");
        waitHelper.waitForElementClickable(cancelButton);
        click(cancelButton);
    }

    /**
     * Check if document appears in client record message is displayed
     *
     * @return boolean
     */
    public boolean isDocumentInClientRecordMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(documentInClientRecordMessage);
        } catch (Exception e) {
            return false;
        }
    }
}
