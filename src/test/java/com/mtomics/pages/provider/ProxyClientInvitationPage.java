package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * ProxyClientInvitationPage - Page Factory implementation for Proxy Client Invitation page
 * 
 * Handles proxy client invitation workflows including:
 * - Proxy client invitation
 * - Proxy relationship setup
 * - Permission assignment
 * - Proxy invitation verification
 * - Proxy access management
 */
public class ProxyClientInvitationPage extends BasePage {

    // Proxy Client Invitation Form Elements
    @FindBy(xpath = "//input[@name='email' or @placeholder='Enter proxy client email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='proxyName' or @placeholder='Enter proxy name']")
    private WebElement proxyNameInput;

    @FindBy(xpath = "//input[@name='relationshipType' or @placeholder='Select relationship type']")
    private WebElement relationshipTypeSelect;

    @FindBy(xpath = "//input[@name='firstName' or @placeholder='Enter first name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName' or @placeholder='Enter last name']")
    private WebElement lastNameInput;

    // Permission Selection
    @FindBy(xpath = "//button[contains(text(),'Select Permissions')]")
    private WebElement selectPermissionsButton;

    @FindBy(xpath = "//div[contains(@class,'permission-list')]")
    private WebElement permissionListContainer;

    @FindBy(xpath = "//input[@type='checkbox' and @name='permissions']")
    private WebElement permissionCheckbox;

    @FindBy(xpath = "//label[contains(text(),'View Documents')]")
    private WebElement viewDocumentsPermission;

    @FindBy(xpath = "//label[contains(text(),'Upload Documents')]")
    private WebElement uploadDocumentsPermission;

    @FindBy(xpath = "//label[contains(text(),'View Appointments')]")
    private WebElement viewAppointmentsPermission;

    @FindBy(xpath = "//label[contains(text(),'Schedule Appointments')]")
    private WebElement scheduleAppointmentsPermission;

    @FindBy(xpath = "//label[contains(text(),'View Lab Results')]")
    private WebElement viewLabResultsPermission;

    @FindBy(xpath = "//label[contains(text(),'Upload Lab Results')]")
    private WebElement uploadLabResultsPermission;

    // Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Send Proxy Invitation')]")
    private WebElement sendProxyInvitationButton;

    @FindBy(xpath = "//button[contains(text(),'View Proxy Relationships')]")
    private WebElement viewProxyRelationshipsButton;

    @FindBy(xpath = "//button[contains(text(),'Manage Proxy Access')]")
    private WebElement manageProxyAccessButton;

    @FindBy(xpath = "//button[contains(text(),'Edit Permissions')]")
    private WebElement editPermissionsButton;

    @FindBy(xpath = "//button[contains(text(),'Remove Proxy Access')]")
    private WebElement removeProxyAccessButton;

    @FindBy(xpath = "//button[contains(text(),'Resend Proxy Invitation')]")
    private WebElement resendProxyInvitationButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Proxy Relationships List
    @FindBy(xpath = "//table[contains(@class,'proxy-relationships')]")
    private WebElement proxyRelationshipsTable;

    @FindBy(xpath = "//tr[contains(@class,'proxy-row')]")
    private WebElement proxyRow;

    // Search and Filter
    @FindBy(xpath = "//input[@placeholder='Search proxy relationships']")
    private WebElement searchInput;

    @FindBy(xpath = "//select[contains(@name,'status')]")
    private WebElement statusFilterSelect;

    @FindBy(xpath = "//select[contains(@name,'relationshipType')]")
    private WebElement relationshipTypeFilterSelect;

    // Status Indicators
    @FindBy(xpath = "//span[contains(text(),'Active')]")
    private WebElement activeStatus;

    @FindBy(xpath = "//span[contains(text(),'Pending')]")
    private WebElement pendingStatus;

    @FindBy(xpath = "//span[contains(text(),'Revoked')]")
    private WebElement revokedStatus;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Proxy invitation sent successfully')]")
    private WebElement proxyInvitationSentMessage;

    @FindBy(xpath = "//div[contains(text(),'Permissions updated successfully')]")
    private WebElement permissionsUpdatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Proxy access removed successfully')]")
    private WebElement proxyAccessRemovedMessage;

    @FindBy(xpath = "//div[contains(text(),'Invalid email format')]")
    private WebElement invalidEmailError;

    @FindBy(xpath = "//div[contains(text(),'Proxy already exists')]")
    private WebElement proxyAlreadyExistsError;

    @FindBy(xpath = "//div[contains(text(),'Verification email sent to')]")
    private WebElement verificationEmailSentMessage;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public ProxyClientInvitationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("ProxyClientInvitationPage initialized");
    }

    /**
     * Check if proxy client invitation page is displayed
     *
     * @return boolean
     */
    public boolean isProxyClientInvitationPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(sendProxyInvitationButton);
        } catch (Exception e) {
            logger.debug("Proxy client invitation page not displayed");
            return false;
        }
    }

    /**
     * Enter proxy email
     *
     * @param email Proxy email address
     */
    public void enterProxyEmail(String email) {
        LogHelper.logStep("Entering proxy email: " + email);
        waitHelper.waitForElementVisible(emailInput);
        enterText(emailInput, email);
    }

    /**
     * Enter proxy name
     *
     * @param proxyName Proxy name
     */
    public void enterProxyName(String proxyName) {
        LogHelper.logStep("Entering proxy name: " + proxyName);
        waitHelper.waitForElementVisible(proxyNameInput);
        enterText(proxyNameInput, proxyName);
    }

    /**
     * Enter first name
     *
     * @param firstName First name
     */
    public void enterFirstName(String firstName) {
        LogHelper.logStep("Entering first name: " + firstName);
        waitHelper.waitForElementVisible(firstNameInput);
        enterText(firstNameInput, firstName);
    }

    /**
     * Enter last name
     *
     * @param lastName Last name
     */
    public void enterLastName(String lastName) {
        LogHelper.logStep("Entering last name: " + lastName);
        waitHelper.waitForElementVisible(lastNameInput);
        enterText(lastNameInput, lastName);
    }

    /**
     * Select relationship type
     *
     * @param relationshipType Relationship type (e.g., spouse, parent, guardian)
     */
    public void selectRelationshipType(String relationshipType) {
        LogHelper.logStep("Selecting relationship type: " + relationshipType);
        waitHelper.waitForElementClickable(relationshipTypeSelect);
        click(relationshipTypeSelect);
    }

    /**
     * Click select permissions button
     */
    public void clickSelectPermissionsButton() {
        LogHelper.logStep("Clicking Select Permissions button");
        waitHelper.waitForElementClickable(selectPermissionsButton);
        click(selectPermissionsButton);
    }

    /**
     * Select permission - View Documents
     */
    public void selectViewDocumentsPermission() {
        LogHelper.logStep("Selecting View Documents permission");
        waitHelper.waitForElementClickable(viewDocumentsPermission);
        click(viewDocumentsPermission);
    }

    /**
     * Select permission - Upload Documents
     */
    public void selectUploadDocumentsPermission() {
        LogHelper.logStep("Selecting Upload Documents permission");
        waitHelper.waitForElementClickable(uploadDocumentsPermission);
        click(uploadDocumentsPermission);
    }

    /**
     * Select permission - View Appointments
     */
    public void selectViewAppointmentsPermission() {
        LogHelper.logStep("Selecting View Appointments permission");
        waitHelper.waitForElementClickable(viewAppointmentsPermission);
        click(viewAppointmentsPermission);
    }

    /**
     * Select permission - Schedule Appointments
     */
    public void selectScheduleAppointmentsPermission() {
        LogHelper.logStep("Selecting Schedule Appointments permission");
        waitHelper.waitForElementClickable(scheduleAppointmentsPermission);
        click(scheduleAppointmentsPermission);
    }

    /**
     * Select permission - View Lab Results
     */
    public void selectViewLabResultsPermission() {
        LogHelper.logStep("Selecting View Lab Results permission");
        waitHelper.waitForElementClickable(viewLabResultsPermission);
        click(viewLabResultsPermission);
    }

    /**
     * Select permission - Upload Lab Results
     */
    public void selectUploadLabResultsPermission() {
        LogHelper.logStep("Selecting Upload Lab Results permission");
        waitHelper.waitForElementClickable(uploadLabResultsPermission);
        click(uploadLabResultsPermission);
    }

    /**
     * Click send proxy invitation button
     */
    public void clickSendProxyInvitationButton() {
        LogHelper.logStep("Clicking Send Proxy Invitation button");
        waitHelper.waitForElementClickable(sendProxyInvitationButton);
        click(sendProxyInvitationButton);
    }

    /**
     * Invite proxy client with basic details
     *
     * @param email Proxy email
     * @param relationshipType Relationship type
     */
    public void inviteProxyClient(String email, String relationshipType) {
        LogHelper.logStep("Inviting proxy client");
        enterProxyEmail(email);
        selectRelationshipType(relationshipType);
        clickSendProxyInvitationButton();
    }

    /**
     * Invite proxy client with full details and permissions
     *
     * @param email Proxy email
     * @param relationshipType Relationship type
     * @param permissions Array of permissions to grant
     */
    public void inviteProxyClientWithPermissions(String email, String relationshipType, String... permissions) {
        LogHelper.logStep("Inviting proxy client with permissions");
        enterProxyEmail(email);
        selectRelationshipType(relationshipType);
        clickSelectPermissionsButton();
        for (String permission : permissions) {
            selectPermissionByName(permission);
        }
        clickSendProxyInvitationButton();
    }

    /**
     * Select permission by name
     *
     * @param permissionName Permission name
     */
    private void selectPermissionByName(String permissionName) {
        switch (permissionName.toLowerCase()) {
            case "view documents":
                selectViewDocumentsPermission();
                break;
            case "upload documents":
                selectUploadDocumentsPermission();
                break;
            case "view appointments":
                selectViewAppointmentsPermission();
                break;
            case "schedule appointments":
                selectScheduleAppointmentsPermission();
                break;
            case "view lab results":
                selectViewLabResultsPermission();
                break;
            case "upload lab results":
                selectUploadLabResultsPermission();
                break;
            default:
                logger.warn("Unknown permission: " + permissionName);
        }
    }

    /**
     * Check if proxy invitation sent message is displayed
     *
     * @return boolean
     */
    public boolean isProxyInvitationSentMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(proxyInvitationSentMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if verification email sent message is displayed
     *
     * @return boolean
     */
    public boolean isVerificationEmailSentMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(verificationEmailSentMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click view proxy relationships button
     */
    public void clickViewProxyRelationshipsButton() {
        LogHelper.logStep("Clicking View Proxy Relationships button");
        waitHelper.waitForElementClickable(viewProxyRelationshipsButton);
        click(viewProxyRelationshipsButton);
    }

    /**
     * Check if proxy relationships table is displayed
     *
     * @return boolean
     */
    public boolean isProxyRelationshipsTableDisplayed() {
        try {
            return waitHelper.isElementDisplayed(proxyRelationshipsTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search proxy relationship by email
     *
     * @param email Email to search
     */
    public void searchProxyByEmail(String email) {
        LogHelper.logStep("Searching proxy by email: " + email);
        waitHelper.waitForElementVisible(searchInput);
        enterText(searchInput, email);
    }

    /**
     * Click manage proxy access button
     */
    public void clickManageProxyAccessButton() {
        LogHelper.logStep("Clicking Manage Proxy Access button");
        waitHelper.waitForElementClickable(manageProxyAccessButton);
        click(manageProxyAccessButton);
    }

    /**
     * Click edit permissions button
     */
    public void clickEditPermissionsButton() {
        LogHelper.logStep("Clicking Edit Permissions button");
        waitHelper.waitForElementClickable(editPermissionsButton);
        click(editPermissionsButton);
    }

    /**
     * Check if permissions updated message is displayed
     *
     * @return boolean
     */
    public boolean isPermissionsUpdatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(permissionsUpdatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click remove proxy access button
     */
    public void clickRemoveProxyAccessButton() {
        LogHelper.logStep("Clicking Remove Proxy Access button");
        waitHelper.waitForElementClickable(removeProxyAccessButton);
        click(removeProxyAccessButton);
    }

    /**
     * Check if proxy access removed message is displayed
     *
     * @return boolean
     */
    public boolean isProxyAccessRemovedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(proxyAccessRemovedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click resend proxy invitation button
     */
    public void clickResendProxyInvitationButton() {
        LogHelper.logStep("Clicking Resend Proxy Invitation button");
        waitHelper.waitForElementClickable(resendProxyInvitationButton);
        click(resendProxyInvitationButton);
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
     * Check if invalid email error is displayed
     *
     * @return boolean
     */
    public boolean isInvalidEmailErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invalidEmailError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if proxy already exists error is displayed
     *
     * @return boolean
     */
    public boolean isProxyAlreadyExistsErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(proxyAlreadyExistsError);
        } catch (Exception e) {
            return false;
        }
    }
}
