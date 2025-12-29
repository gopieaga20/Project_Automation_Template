package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * ClientInvitationPage - Page Factory implementation for Provider Client Invitation page
 * Based on: apps/web/src/features/invitations
 * 
 * Handles client invitation workflows including:
 * - Client invitation with valid email
 * - First and last name assignment
 * - Initial survey assignment during invitation
 * - Multiple client invitations
 * - Resend invitation
 * - View pending invitations
 * - Revoke client invitation
 */
public class ClientInvitationPage extends BasePage {

    // Invitation Form Elements
    @FindBy(xpath = "//input[@name='email' or @placeholder='Enter client email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='firstName' or @placeholder='Enter first name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName' or @placeholder='Enter last name']")
    private WebElement lastNameInput;

    // Survey Selection
    @FindBy(xpath = "//button[contains(text(),'Select Surveys')]")
    private WebElement selectSurveysButton;

    @FindBy(xpath = "//div[contains(@class,'survey-list')]")
    private WebElement surveyListContainer;

    @FindBy(xpath = "//input[@type='checkbox' and @name='surveys']")
    private WebElement surveyCheckbox;

    // Invitation Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Send Invitation')]")
    private WebElement sendInvitationButton;

    @FindBy(xpath = "//button[contains(text(),'Invite Multiple Clients')]")
    private WebElement inviteMultipleClientsButton;

    @FindBy(xpath = "//button[contains(text(),'Add Another')]")
    private WebElement addAnotherButton;

    // Pending Invitations
    @FindBy(xpath = "//button[contains(text(),'View Pending Invitations')]")
    private WebElement viewPendingInvitationsButton;

    @FindBy(xpath = "//table[contains(@class,'invitations')]")
    private WebElement invitationsTable;

    @FindBy(xpath = "//tr[contains(@class,'invitation-row')]")
    private WebElement invitationRow;

    // Invitation Actions
    @FindBy(xpath = "//button[contains(text(),'Resend Invitation')]")
    private WebElement resendInvitationButton;

    @FindBy(xpath = "//button[contains(text(),'Revoke Invitation')]")
    private WebElement revokeInvitationButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Filter and Search
    @FindBy(xpath = "//input[@placeholder='Search invitations']")
    private WebElement searchInput;

    @FindBy(xpath = "//select[contains(@name,'status')]")
    private WebElement statusFilterSelect;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Invitation sent successfully')]")
    private WebElement invitationSentMessage;

    @FindBy(xpath = "//div[contains(text(),'Email already invited')]")
    private WebElement emailAlreadyInvitedError;

    @FindBy(xpath = "//div[contains(text(),'Invalid email format')]")
    private WebElement invalidEmailFormatError;

    @FindBy(xpath = "//div[contains(text(),'Invitation resent successfully')]")
    private WebElement invitationResentMessage;

    @FindBy(xpath = "//div[contains(text(),'Invitation revoked successfully')]")
    private WebElement invitationRevokedMessage;

    @FindBy(xpath = "//div[contains(text(),'Invitation email sent to')]")
    private WebElement invitationEmailSentMessage;

    // Pending Invitation Status
    @FindBy(xpath = "//span[contains(text(),'Pending')]")
    private WebElement pendingStatus;

    @FindBy(xpath = "//span[contains(text(),'Accepted')]")
    private WebElement acceptedStatus;

    @FindBy(xpath = "//span[contains(text(),'Expired')]")
    private WebElement expiredStatus;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public ClientInvitationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("ClientInvitationPage initialized");
    }

    /**
     * Check if client invitation page is displayed
     *
     * @return boolean
     */
    public boolean isClientInvitationPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(sendInvitationButton);
        } catch (Exception e) {
            logger.debug("Client invitation page not displayed");
            return false;
        }
    }

    /**
     * Enter client email
     *
     * @param email Client email address
     */
    public void enterClientEmail(String email) {
        LogHelper.logStep("Entering client email: " + email);
        waitHelper.waitForElementVisible(emailInput);
        enterText(emailInput, email);
    }

    /**
     * Enter client first name
     *
     * @param firstName Client first name
     */
    public void enterFirstName(String firstName) {
        LogHelper.logStep("Entering client first name: " + firstName);
        waitHelper.waitForElementVisible(firstNameInput);
        enterText(firstNameInput, firstName);
    }

    /**
     * Enter client last name
     *
     * @param lastName Client last name
     */
    public void enterLastName(String lastName) {
        LogHelper.logStep("Entering client last name: " + lastName);
        waitHelper.waitForElementVisible(lastNameInput);
        enterText(lastNameInput, lastName);
    }

    /**
     * Click select surveys button
     */
    public void clickSelectSurveysButton() {
        LogHelper.logStep("Clicking Select Surveys button");
        waitHelper.waitForElementClickable(selectSurveysButton);
        click(selectSurveysButton);
    }

    /**
     * Select survey by name
     *
     * @param surveyName Survey name
     */
    public void selectSurveyByName(String surveyName) {
        LogHelper.logStep("Selecting survey: " + surveyName);
        // Implementation depends on actual structure
        waitHelper.waitForElementVisible(surveyListContainer);
    }

    /**
     * Click send invitation button
     */
    public void clickSendInvitationButton() {
        LogHelper.logStep("Clicking Send Invitation button");
        waitHelper.waitForElementClickable(sendInvitationButton);
        click(sendInvitationButton);
    }

    /**
     * Click invite multiple clients button
     */
    public void clickInviteMultipleClientsButton() {
        LogHelper.logStep("Clicking Invite Multiple Clients button");
        waitHelper.waitForElementClickable(inviteMultipleClientsButton);
        click(inviteMultipleClientsButton);
    }

    /**
     * Click add another button for multiple invitations
     */
    public void clickAddAnotherButton() {
        LogHelper.logStep("Clicking Add Another button");
        waitHelper.waitForElementClickable(addAnotherButton);
        click(addAnotherButton);
    }

    /**
     * Invite single client with valid details
     *
     * @param email Client email
     * @param firstName Client first name
     * @param lastName Client last name
     */
    public void inviteClientWithValidDetails(String email, String firstName, String lastName) {
        LogHelper.logStep("Inviting client with valid details");
        enterClientEmail(email);
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSendInvitationButton();
    }

    /**
     * Invite client with surveys
     *
     * @param email Client email
     * @param firstName Client first name
     * @param lastName Client last name
     * @param surveyNames Survey names to assign
     */
    public void inviteClientWithSurveys(String email, String firstName, String lastName, String... surveyNames) {
        LogHelper.logStep("Inviting client with surveys");
        enterClientEmail(email);
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSelectSurveysButton();
        for (String surveyName : surveyNames) {
            selectSurveyByName(surveyName);
        }
        clickSendInvitationButton();
    }

    /**
     * Check if invitation sent message is displayed
     *
     * @return boolean
     */
    public boolean isInvitationSentMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invitationSentMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if email already invited error is displayed
     *
     * @return boolean
     */
    public boolean isEmailAlreadyInvitedErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(emailAlreadyInvitedError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if invalid email format error is displayed
     *
     * @return boolean
     */
    public boolean isInvalidEmailFormatErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invalidEmailFormatError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click view pending invitations button
     */
    public void clickViewPendingInvitationsButton() {
        LogHelper.logStep("Clicking View Pending Invitations button");
        waitHelper.waitForElementClickable(viewPendingInvitationsButton);
        click(viewPendingInvitationsButton);
    }

    /**
     * Check if pending invitations are displayed
     *
     * @return boolean
     */
    public boolean isPendingInvitationsDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invitationsTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search invitation by email
     *
     * @param email Email to search
     */
    public void searchInvitationByEmail(String email) {
        LogHelper.logStep("Searching invitation by email: " + email);
        waitHelper.waitForElementVisible(searchInput);
        enterText(searchInput, email);
    }

    /**
     * Click resend invitation button for a specific pending invitation
     */
    public void clickResendInvitationButton() {
        LogHelper.logStep("Clicking Resend Invitation button");
        waitHelper.waitForElementClickable(resendInvitationButton);
        click(resendInvitationButton);
    }

    /**
     * Check if invitation resent message is displayed
     *
     * @return boolean
     */
    public boolean isInvitationResentMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invitationResentMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click revoke invitation button
     */
    public void clickRevokeInvitationButton() {
        LogHelper.logStep("Clicking Revoke Invitation button");
        waitHelper.waitForElementClickable(revokeInvitationButton);
        click(revokeInvitationButton);
    }

    /**
     * Check if invitation revoked message is displayed
     *
     * @return boolean
     */
    public boolean isInvitationRevokedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invitationRevokedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Invite multiple clients
     *
     * @param clientEmails Array of client emails
     */
    public void inviteMultipleClients(String... clientEmails) {
        LogHelper.logStep("Inviting multiple clients");
        for (int i = 0; i < clientEmails.length; i++) {
            enterClientEmail(clientEmails[i]);
            if (i < clientEmails.length - 1) {
                clickAddAnotherButton();
            }
        }
        clickSendInvitationButton();
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
