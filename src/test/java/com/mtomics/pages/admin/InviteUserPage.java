package com.mtomics.pages.admin;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.DropdownHelper;
import com.mtomics.utils.LogHelper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * InviteUserPage - Page Factory implementation for Invite User functionality
 * Based on: apps/web/src/features/user-management/components/new-invite.tsx
 */
public class InviteUserPage extends BasePage {

    // Page Factory elements
    @FindBy(xpath = "//h2[contains(text(),'Add User')]")
    private WebElement addUserHeading;

    @FindBy(xpath = "//input[@placeholder='Enter email address' or @type='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='First name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Last name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//button[@role='combobox']")
    private WebElement roleDropdownTrigger;

    @FindBy(xpath = "//select[@aria-hidden='true']")
    private WebElement roleDropdown;

    // Role options in dropdown
    @FindBy(xpath = "//div[@role='option' and contains(text(),'Provider')]")
    private WebElement providerRoleOption;

    @FindBy(xpath = "//div[@role='option' and contains(text(),'Manager')]")
    private WebElement managerRoleOption;

    @FindBy(xpath = "//div[@role='option' and contains(text(),'Practice Admin')]")
    private WebElement practiceAdminRoleOption;

    @FindBy(xpath = "//button[contains(text(),'Send Invite')]")
    private WebElement sendInviteButton;

    @FindBy(xpath = "//button[contains(text(),'Edit Email Template')]")
    private WebElement editEmailTemplateButton;

    // Preview area
    @FindBy(xpath = "//*[@id=\"radix-_r_e_\"]/div/form/div[4]")
    private WebElement emailPreviewArea;

    // Toast messages
    @FindBy(xpath = "//div[contains(text(),'Invite sent successfully')]")
    private WebElement inviteSentSuccessToast;

    @FindBy(xpath = "//div[contains(text(),'User already exist')]")
    private WebElement userExistsErrorToast;

    @FindBy(xpath = "//div[contains(text(),'Invitation already sent')]")
    private WebElement invitationAlreadySentToast;

    // Form validation messages
    @FindBy(xpath = "//p[contains(@class,'text-red-500') and contains(text(),'Email')]")
    private WebElement emailValidationMessage;

    @FindBy(xpath = "//p[contains(@class,'text-red-500') and contains(text(),'First name')]")
    private WebElement firstNameValidationMessage;

    @FindBy(xpath = "//p[contains(@class,'text-red-500') and contains(text(),'Last name')]")
    private WebElement lastNameValidationMessage;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public InviteUserPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("InviteUserPage initialized");
    }

    /**
     * Check if invite user dialog is displayed
     * 
     * @return boolean
     */
    public boolean isInviteUserDialogDisplayed() {
        try {
            return waitHelper.isElementDisplayed(addUserHeading);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enter email address
     * 
     * @param email Email address
     */
    public void enterEmail(String email) {
        LogHelper.logStep("Entering email: " + email);
        waitHelper.waitForElementVisible(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        logger.debug("Email entered: {}", email);
    }

    /**
     * Enter first name
     * 
     * @param firstName First name
     */
    public void enterFirstName(String firstName) {
        LogHelper.logStep("Entering first name: " + firstName);
        waitHelper.waitForElementVisible(firstNameInput);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        logger.debug("First name entered: {}", firstName);
    }

    /**
     * Enter last name
     * 
     * @param lastName Last name
     */
    public void enterLastName(String lastName) {
        LogHelper.logStep("Entering last name: " + lastName);
        waitHelper.waitForElementVisible(lastNameInput);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        logger.debug("Last name entered: {}", lastName);
    }

    /**
     * Select role from dropdown
     * 
     * @param role Role name (Provider, Manager, Practice Admin)
     */
    public void selectRolea(String role) {
        LogHelper.logStep("Selecting role: " + role);
        waitHelper.waitForElementVisible(roleDropdownTrigger);
        roleDropdownTrigger.click();

        // Wait a moment for dropdown to open
        waitHelper.hardWait(500);

        switch (role.toLowerCase()) {
            case "provider":
                waitHelper.waitForElementClickable(providerRoleOption);
                providerRoleOption.click();
                break;
            case "manager":
                waitHelper.waitForElementClickable(managerRoleOption);
                managerRoleOption.click();
                break;
            case "practice admin":
            case "practice_admin":
                waitHelper.waitForElementClickable(practiceAdminRoleOption);
                practiceAdminRoleOption.click();
                break;
            default:
                logger.error("Invalid role: {}", role);
                throw new IllegalArgumentException("Invalid role: " + role);
        }
        logger.debug("Role selected: {}", role);
    }


    public void selectRole(String role) {
        LogHelper.logStep("Selecting role: " + role);
        DropdownHelper dropdownHelper = new DropdownHelper(driver);
        dropdownHelper.selectByVisibleText(roleDropdown, role);
    }


    /**
     * Click send invite button
     */
    public void clickSendInvite() {
        LogHelper.logStep("Clicking Send Invite button");
        waitHelper.waitForElementClickable(sendInviteButton);
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
        javaScriptExecutor.executeScript("arguments[0].click();", sendInviteButton);
        logger.info("Send Invite button clicked");
    }

    /**
     * Invite user with all details
     * 
     * @param email     Email address
     * @param firstName First name
     * @param lastName  Last name
     * @param role      Role
     */
    public void inviteUser(String email, String firstName, String lastName, String role) {
        LogHelper.logStep("Inviting user: " + email);
        enterEmail(email);
        enterFirstName(firstName);
        enterLastName(lastName);
        selectRole(role);
        clickSendInvite();
        logger.info("User invitation sent");
    }

    /**
     * Invite provider
     * 
     * @param email     Email address
     * @param firstName First name
     * @param lastName  Last name
     */
    public void inviteProvider(String email, String firstName, String lastName) {
        LogHelper.logStep("Inviting provider: " + email);
        inviteUser(email, firstName, lastName, "Provider");
    }

    /**
     * Invite manager
     * 
     * @param email     Email address
     * @param firstName First name
     * @param lastName  Last name
     */
    public void inviteManager(String email, String firstName, String lastName) {
        LogHelper.logStep("Inviting manager: " + email);
        inviteUser(email, firstName, lastName, "Manager");
    }

    /**
     * Check if invite sent success message is displayed
     * 
     * @return boolean
     */
    public boolean isInviteSentSuccessDisplayed() {
        try {
            // Wait a bit for toast to appear
            waitHelper.hardWait(2000);
            return inviteSentSuccessToast.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isFirstNameValidationMessageDisplayed() {
        try {
            return firstNameValidationMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLastNameValidationMessageDisplayed() {
        try {
            return lastNameValidationMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if user exists error is displayed
     * 
     * @return boolean
     */
    public boolean isUserExistsErrorDisplayed() {
        try {
            // Wait a bit for toast to appear
            waitHelper.hardWait(1000);
            return userExistsErrorToast.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if invitation already sent error is displayed
     * 
     * @return boolean
     */
    public boolean isInvitationAlreadySentErrorDisplayed() {
        try {
            // Wait a bit for toast to appear
            waitHelper.hardWait(1000);
            return invitationAlreadySentToast.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if email validation message is displayed
     * 
     * @return boolean
     */
    public boolean isEmailValidationMessageDisplayed() {
        try {
            return emailValidationMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click edit email template button
     */
    public void clickEditEmailTemplate() {
        LogHelper.logStep("Clicking Edit Email Template button");
        waitHelper.waitForElementClickable(editEmailTemplateButton);
        editEmailTemplateButton.click();
        logger.info("Edit Email Template button clicked");
    }

    /**
     * Check if email preview is displayed
     * 
     * @return boolean
     */
    public boolean isEmailPreviewDisplayed() {
        try {
            waitHelper.hardWait(8000);
            return waitHelper.isElementDisplayed(emailPreviewArea);
        } catch (Exception e) {
            return false;
        }
    }


}
